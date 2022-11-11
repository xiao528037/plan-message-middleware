# plan-message-middleware

这是我学习消息中间件的代码

# rocketmq集群启动

## Node A Master配置文件

```yaml
#所属集群名字
brokerClusterName=rocketmq-cluster
  #broker名字，注意此处不同的配置文件填写的不一样
brokerName=broker-a
  #0 表示 Master，>0 表示 Slave
brokerId=0
  #nameServer地址，多个之间用分号分割
namesrvAddr=192.168.2.110:9876;192.168.2.120:9876
  #在发送消息时，自动创建服务器不存在的topic，默认创建的队列数
defaultTopicQueueNums=4
  #是否允许 Broker 自动创建Topic，建议线下开启，线上关闭
autoCreateTopicEnable=true
  #是否允许 Broker 自动创建订阅组，建议线下开启，线上关闭
autoCreateSubscriptionGroup=true
  #Broker 对外服务的监听端口
listenPort=10911
  #删除文件时间点，默认凌晨 4点
deleteWhen=04
  #文件保留时间，默认 48 小时
fileReservedTime=120
  #commitLog每个文件的大小默认1G
mapedFileSizeCommitLog=1073741824
  #ConsumeQueue每个文件默认存30W条，根据业务情况调整
mapedFileSizeConsumeQueue=300000
  #destroyMapedFileIntervalForcibly=120000
  #redeleteHangedFileInterval=120000
  #检测物理文件磁盘空间
diskMaxUsedSpaceRatio=88
  #存储路径
storePathRootDir=/home/rocketmq/store/broker-a
  #commitLog 存储路径
storePathCommitLog=/home/rocketmq/store/broker-a/commitlog
  #消费队列存储路径存储路径
storePathConsumeQueue=/home/rocketmq/store/broker-a/consumequeue
  #消息索引存储路径
storePathIndex=/home/rocketmq/store/broker-a/index
  #checkpoint 文件存储路径
storeCheckpoint=/home/rocketmq/store/broker-a/checkpoint
  #abort 文件存储路径
abortFile=/home/rocketmq/store/broker-a/abort
  #限制的消息大小
maxMessageSize=65536
  #flushCommitLogLeastPages=4
  #flushConsumeQueueLeastPages=2
  #flushCommitLogThoroughInterval=10000
  #flushConsumeQueueThoroughInterval=60000
  #Broker 的角色
  #- ASYNC_MASTER 异步复制Master
  #- SYNC_MASTER 同步双写Master
  #- SLAVE
brokerRole=SYNC_MASTER
  #刷盘方式
  #- ASYNC_FLUSH 异步刷盘
  #- SYNC_FLUSH 同步刷盘
flushDiskType=SYNC_FLUSH
#checkTransactionMessageEnable=false
#发消息线程池数量
#sendMessageThreadPoolNums=128
#拉消息线程池数量
#pullMessageThreadPoolNums=128
# 开启SQL92规则过滤
enablePropertyFilter=true
```

## Node A Slave配置文件

```yaml
#所属集群名字
brokerClusterName=rocketmq-cluster
  #broker名字，注意此处不同的配置文件填写的不一样
brokerName=broker-a
  #0 表示 Master，>0 表示 Slave
brokerId=1
  #nameServer地址，分号分割
namesrvAddr=192.168.2.110:9876;192.168.2.120:9876
  #在发送消息时，自动创建服务器不存在的topic，默认创建的队列数
defaultTopicQueueNums=4
  #是否允许 Broker 自动创建Topic，建议线下开启，线上关闭
autoCreateTopicEnable=true
  #是否允许 Broker 自动创建订阅组，建议线下开启，线上关闭
autoCreateSubscriptionGroup=true
  #Broker 对外服务的监听端口
listenPort=11011
  #删除文件时间点，默认凌晨 4点
deleteWhen=04
  #文件保留时间，默认 48 小时
fileReservedTime=120
  #commitLog每个文件的大小默认1G
mapedFileSizeCommitLog=1073741824
  #ConsumeQueue每个文件默认存30W条，根据业务情况调整
mapedFileSizeConsumeQueue=300000
  #destroyMapedFileIntervalForcibly=120000
  #redeleteHangedFileInterval=120000
  #检测物理文件磁盘空间
diskMaxUsedSpaceRatio=88
  #存储路径
storePathRootDir=/home/rocketmq/store/broker-a-slave
  #commitLog 存储路径
storePathCommitLog=/home/rocketmq/store/broker-a-slave/commitlog
  #消费队列存储路径存储路径
storePathConsumeQueue=/home/rocketmq/store/broker-a-slave/consumequeue
  #消息索引存储路径
storePathIndex=/home/rocketmq/store/broker-a-slave/index
  #checkpoint 文件存储路径
storeCheckpoint=/home/rocketmq/store/broker-a-slave/checkpoint
  #abort 文件存储路径
abortFile=/home/rocketmq/store/broker-a-slave/abort
  #限制的消息大小
maxMessageSize=65536
  #flushCommitLogLeastPages=4
  #flushConsumeQueueLeastPages=2
  #flushCommitLogThoroughInterval=10000
  #flushConsumeQueueThoroughInterval=60000
  #Broker 的角色
  #- ASYNC_MASTER 异步复制Master
  #- SYNC_MASTER 同步双写Master
  #- SLAVE
brokerRole=SLAVE
  #刷盘方式
  #- ASYNC_FLUSH 异步刷盘
  #- SYNC_FLUSH 同步刷盘
flushDiskType=ASYNC_FLUSH
#checkTransactionMessageEnable=false
#发消息线程池数量
#sendMessageThreadPoolNums=128
#拉消息线程池数量
#pullMessageThreadPoolNums=128
# 开启SQL92规则过滤
enablePropertyFilter=true

```

## Node B Master配置文件

```yaml
#所属集群名字
brokerClusterName=rocketmq-cluster
  #broker名字，注意此处不同的配置文件填写的不一样
brokerName=broker-b
  #0 表示 Master，>0 表示 Slave
brokerId=0
  #nameServer地址，分号分割
namesrvAddr=192.168.2.110:9876;192.168.2.120:9876
  #在发送消息时，自动创建服务器不存在的topic，默认创建的队列数
defaultTopicQueueNums=4
  #是否允许 Broker 自动创建Topic，建议线下开启，线上关闭
autoCreateTopicEnable=true
  #是否允许 Broker 自动创建订阅组，建议线下开启，线上关闭
autoCreateSubscriptionGroup=true
  #Broker 对外服务的监听端口
listenPort=10911
  #删除文件时间点，默认凌晨 4点
deleteWhen=04
  #文件保留时间，默认 48 小时
fileReservedTime=120
  #commitLog每个文件的大小默认1G
mapedFileSizeCommitLog=1073741824
  #ConsumeQueue每个文件默认存30W条，根据业务情况调整
mapedFileSizeConsumeQueue=300000
  #destroyMapedFileIntervalForcibly=120000
  #redeleteHangedFileInterval=120000
  #检测物理文件磁盘空间
diskMaxUsedSpaceRatio=88
  #存储路径(注意，如果在一台机器上启动多个broker，这个路径要区分开)
storePathRootDir=/home/rocketmq/store/broker-b
  #commitLog 存储路径
storePathCommitLog=/home/rocketmq/store/broker-b/commitlog
  #消费队列存储路径存储路径
storePathConsumeQueue=/home/rocketmq/store/broker-b/consumequeue
  #消息索引存储路径
storePathIndex=/home/rocketmq/store/broker-b/index
  #checkpoint 文件存储路径
storeCheckpoint=/home/rocketmq/store/broker-b/checkpoint
  #abort 文件存储路径
abortFile=/home/rocketmq/store/broker-b/abort
  #限制的消息大小
maxMessageSize=65536
  #flushCommitLogLeastPages=4
  #flushConsumeQueueLeastPages=2
  #flushCommitLogThoroughInterval=10000
  #flushConsumeQueueThoroughInterval=60000
  #Broker 的角色
  #- ASYNC_MASTER 异步复制Master
  #- SYNC_MASTER 同步双写Master
  #- SLAVE
brokerRole=SYNC_MASTER
  #刷盘方式
  #- ASYNC_FLUSH 异步刷盘
  #- SYNC_FLUSH 同步刷盘
flushDiskType=SYNC_FLUSH
#checkTransactionMessageEnable=false
#发消息线程池数量
#sendMessageThreadPoolNums=128
#拉消息线程池数量
#pullMessageThreadPoolNums=128
# 开启SQL92规则过滤
enablePropertyFilter=true

```

## Node B Slave配置文件

```yaml
#所属集群名字
brokerClusterName=rocketmq-cluster
  #broker名字，注意此处不同的配置文件填写的不一样
brokerName=broker-b
  #0 表示 Master，>0 表示 Slave
brokerId=1
  #nameServer地址，分号分割
namesrvAddr=192.168.2.110:9876;192.168.2.120:9876
  #在发送消息时，自动创建服务器不存在的topic，默认创建的队列数
defaultTopicQueueNums=4
  #是否允许 Broker 自动创建Topic，建议线下开启，线上关闭
autoCreateTopicEnable=true
  #是否允许 Broker 自动创建订阅组，建议线下开启，线上关闭
autoCreateSubscriptionGroup=true
  #Broker 对外服务的监听端口
listenPort=11011
  #删除文件时间点，默认凌晨 4点
deleteWhen=04
  #文件保留时间，默认 48 小时
fileReservedTime=120
  #commitLog每个文件的大小默认1G
mapedFileSizeCommitLog=1073741824
  #ConsumeQueue每个文件默认存30W条，根据业务情况调整
mapedFileSizeConsumeQueue=300000
  #destroyMapedFileIntervalForcibly=120000
  #redeleteHangedFileInterval=120000
  #检测物理文件磁盘空间
diskMaxUsedSpaceRatio=88
  #存储路径
storePathRootDir=/home/rocketmq/store/broker-b-slave
  #commitLog 存储路径
storePathCommitLog=/home/rocketmq/store/broker-b-slave/commitlog
  #消费队列存储路径存储路径
storePathConsumeQueue=/home/rocketmq/store/broker-b-slave/consumequeue
  #消息索引存储路径
storePathIndex=/home/rocketmq/store/broker-b-slave/index
  #checkpoint 文件存储路径
storeCheckpoint=/home/rocketmq/store/broker-b-slave/checkpoint
  #abort 文件存储路径
abortFile=/home/rocketmq/store/broker-b-slave/abort
  #限制的消息大小
maxMessageSize=65536
  #flushCommitLogLeastPages=4
  #flushConsumeQueueLeastPages=2
  #flushCommitLogThoroughInterval=10000
  #flushConsumeQueueThoroughInterval=60000
  #Broker 的角色
  #- ASYNC_MASTER 异步复制Master
  #- SYNC_MASTER 同步双写Master
  #- SLAVE
brokerRole=SLAVE
  #刷盘方式
  #- ASYNC_FLUSH 异步刷盘
  #- SYNC_FLUSH 同步刷盘
flushDiskType=ASYNC_FLUSH
#checkTransactionMessageEnable=false
#发消息线程池数量
#sendMessageThreadPoolNums=128
#拉消息线程池数量
#pullMessageThreadPoolNums=128
# 开启SQL92规则过滤
enablePropertyFilter=true

```

## Node A启动

```sh
cd /opt/rokcet/

#启动nameserver
nohup sh bin/mqnamesrv &

# 启动master a
nohup sh bin/mqbroker -c /opt/config/broker-a.properties &   

# 启动slave b
nohup sh bin/mqbroker -c /opt/config/broker-b-s.properties &   
```

## Node B启动

```sh
cd /opt/rokcet/

# 启动nameserver
nohup sh bin/mqnamesrv &

# 启动master b
nohup sh bin/mqbroker -c /opt/config/broker-b.properties &   
# 启动slave a
nohup sh bin/mqbroker -c /opt/config/broker-a-s.properties & 
```

## 关闭服务

```shell
cd /opt/rokcet/

# 关闭broker
sh bin/mqshutdown broker

# 关闭namesrv
sh bin/mqshutdown namesrv
```
