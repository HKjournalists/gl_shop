#!/bin/sh

export JAVA_HOME=/usr/local/java/jdk1.7.0_71
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar:$JAVA_HOME/lib
export PATH=$PATH:$JAVA_HOME/bin
#export PATH JAVA_HOME CLASSPATH


export BASEDIR=/usr/local/glshop/schedule
export CLASSPATH=$CLASSPATH:$BASEDIR/conf

java -server -ms128m -mx512m -classpath /usr/local/glshop/schedule/conf -Dbase.dir=/usr/local/glshop/schedule -jar /usr/local/glshop/schedule/schedule-lib.jar >> /usr/local/glshop/schedule/schedule-lib.log 2>&1 &

echo "schedule started."
#java -jar /usr/local/glshop/e.jar >> /usr/local/glshop/e.log 2>&1 &

#export BASEDIR=/usr/local/glshop/schedule
#export CLASSPATH=$BASEDIR/conf
#nohup java -server -classpath $CLASSPATH -Dbase.dir=$BASEDIR -jar $BASEDIR/schedule-lib.jar > $BASEDIR/nohup.out & tail -f $BASEDIR/nohup.out

