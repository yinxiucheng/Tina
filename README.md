# Tina
创建项目Tina， tian！

【功能】2018.8.8 添加 多线程下载功能

【功能】2018.08.12 多线程下载添加 数据库保存记录

  2018/8/12 遇到的坑
  
  Greendao 接入的坑， ThreadInfo的 _id 主键设置的数据类型为 long，而不是Long导致无法自动生成，每次只有一个在表中。
  
  数据库相关的操作最好是另起线程来操作，用 excutor来执行，简单方便。
  
  数据库中的 增、删操作添加 锁后，出异常，之后去掉了。
