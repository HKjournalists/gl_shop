package com.appabc.tools.utils.taskmanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

/**
 * @author     : 刘剑
 * @version    : 1.0
 * Create Date : 2014年12月31日 下午2:01:15
 **/
public class XmppFetchCenterManager {
	
	private Logger log = Logger.getLogger(this.getClass());

	/**并发加载的数量*/
	private int loaderCounts = 3;

	private static XmppFetchCenterManager manager;

	private boolean isListenering = false;

	private boolean isLoader = true;

	/**任务执行堆栈*/
	private Stack<XmppFetchTask> tasksStack = new Stack<XmppFetchTask>();

	/**正在执行的任务*/
	private List<XmppFetchTask> runningTask = new ArrayList<XmppFetchTask>();

	/**网络请求的总线程数*/
	private ExecutorService netExcutor;

	/**任务添加队列*/
	private LinkedBlockingQueue<XmppFetchTask> taskQueue = new LinkedBlockingQueue<XmppFetchTask>();

	/**单例*/
	private XmppFetchCenterManager() {
		setLoaderCounts(20);
		startAddTaskListener();
	}

	/**单例方式获取实例*/
	public static synchronized XmppFetchCenterManager getIntance() {
		if (manager == null) {
			manager = new XmppFetchCenterManager();
		}
		return manager;
	}

	public void setLoaderCounts(int loaderCounts) {
		if (this.loaderCounts != loaderCounts && loaderCounts > 0) {
			if (netExcutor != null) {
				netExcutor.shutdown();
			}

			netExcutor = Executors.newFixedThreadPool(loaderCounts, new ThreadFactory() {

				private final AtomicInteger poolNumber = new AtomicInteger(1);

				public Thread newThread(Runnable r) {
					return new Thread(r, "Images-loader-" + poolNumber.getAndIncrement());
				}
			});
		}
		this.loaderCounts = loaderCounts;

	}

	/**
	 * 停止加载
	 */
	public void stopLoader() {
		isLoader = false;
	}

	/**
	 * 开始加载
	 */
	public void startLoader() {
		isLoader = true;
		schedule();
	}

	/***
	 * 清空堆栈正在加载的任务
	 */
	public void clear() {
		tasksStack.clear();
	}

	/***
	 * 退出应用时候调用的
	 */
	public void exit(){
		try{
			isListenering = false;
			isLoader = false;
			netExcutor.shutdown();
			
			tasksStack.clear();
			runningTask.clear();
			taskQueue.clear();
		}catch(Exception e){
			e.printStackTrace();
			log.error("exit error "+e.getMessage());
		}
	}
	
	/***
	 * 任务的核心调度
	 */
	protected void schedule() {
		if (runningTask.size() < loaderCounts && isLoader) {
			if (!tasksStack.isEmpty()) {
				int addNumber = loaderCounts - runningTask.size();
				addNumber = addNumber > tasksStack.size() ? tasksStack.size() : addNumber;
				for (int i = 0; i < addNumber; i++) {
					XmppFetchTask task = tasksStack.pop();
					runningTask.add(task);
					netExcutor.execute(task);
//					log.debug("start image Task:" + task.getUrl());
				}
//				log.info("runningTask.size() :" + runningTask.size());
			}else{
//				log.info("no net image task");
			}
		}
	}

	/***
	 * 任务完成
	 * @param XmppFetchTask
	 */
	protected void finishTask(XmppFetchTask FetchTask) {
//		log.debug("end image Task:" + XmppFetchTask.getUrl());
		runningTask.remove(FetchTask);
		schedule();
	}

	/**
	 * 添加任务
	 * @param task
	 */
	public void addTask(XmppFetchTask task) {
		taskQueue.offer(task);
	}

	/***
	 * 下载监听
	 */
	protected void startAddTaskListener() {
		if (!isListenering) {
			isListenering = true;
			new Thread() {
				public void run() {
					log.info("startAddTaskListener ===");
					while (isListenering) {
						try {
							//接受任务
							XmppFetchTask task = taskQueue.take();
							//将任务放到待执行的队列中
							tasksStack.push(task);
							if (isLoader) {
								//重新调度
								schedule();
							}
						} catch (Exception e) {
							e.printStackTrace();
							log.info("addTaskListener error:" + e.getMessage());
						}
					}
				}
			}.start();
		}
	}
}
