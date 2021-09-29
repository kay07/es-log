# es-log
## 使用java从elasticsearch中采集日志
### 实现的功能
1.布尔多条件查询

2.模糊查询

3.时间范围查询

### 注意事项
1.修改servimpl实现类中的索引名为日志的索引名

2.修改bootstrap配置文件中nacos的地址与其他信息

3.将application配置文件中内容放入nacos中，只需要es的配置即可

4.pom中system-platform-core包在https://github.com/kay07/system.git

