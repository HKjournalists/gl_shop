About the VO, DTO, DAO, PO, VO comment in the java world
http://yinchunjian.iteye.com/blog/758196
About Maven:
http://juvenshun.iteye.com/blog/359256 [Maven最佳实践：Maven仓库]
http://my.oschina.net/davyzhong/blog/88614

db info:
username=glshop
password=glshop
database=glshop

current project operator desc:
1: check out this project file from the svn;
2: import the project into the eclipse tool;
3: then you need to configure your maven env;
if you arenot install the maven, then you need to download the maven lib
and confire the maven env to path;
  ++(1): configure the remote repository in the local settings.xml
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++  
  <profile>  
      <id>dev</id>  
      <!-- repositories and pluginRepositories here-->
	<repositories>
			<repository>
				<id>Nexus</id>
				<name>Nexus Public Mirror</name>
				<url>http://192.168.1.224:8081/nexus/content/groups/public/</url>
				<releases>
					<enabled>true</enabled>
				</releases>
				<snapshots>
					<enabled>true</enabled>
				</snapshots>
			</repository>
		</repositories>
	<pluginRepositories>
		<pluginRepository>
			<id>Nexus</id>
			<name>Nexus Public Mirror</name>
			<url>http://192.168.1.224:8081/nexus/content/groups/public/</url>
			<releases>
				<enabled>true</enabled>
			</releases>
			<snapshots>
				<enabled>true</enabled>
			</snapshots>
		</pluginRepository>
	</pluginRepositories>	  
    </profile>  
	  
  </profiles>
<activeProfiles>  
		<activeProfile>dev</activeProfile>  
	  </activeProfiles>
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  ++(2): configure the remote repository server info:
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
  <server>    
      <id>releases</id>    
      <username>admin</username>    
      <password>admin123</password>    
    </server>    
    <server>    
      <id>snapshots</id>    
      <username>admin</username>    
      <password>admin123</password>    
    </server>
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
4: then you can execute: mvn clean, mvn install, mvn deploy;
mvn clean   : clean the project with maven;
mvn install : install the project with maven;
mvn deploy  : deploy to the remote repository;

need to fixed the problem:
1) queryForObejct throws some exception when the result is null.
2) controller return null the request is have not json data.

to do task:
1) 任务调度的工具集成
