#<center>项目构建手册</center>
*damon*

*2014.10.17*
****

+ 友情提示：查阅本文档的最佳工具是MarkdownPad，可根据个人喜好酌情选择。

####1.下载基础仓库

>打开git命令窗口，执行如下命令：

	//切到本机干净的目录，例如本人的e盘根目录，本例基础装有git管理工具	
	克隆基础仓库到本地
	
####2.创建项目

>打开IDE，执行如下命令：

	//新建项目（本例IDE使用STS）
	File-->New-->Dynamic Web Project
	//编辑项目名，Next，Next，编辑工程目录
	xmkf-->Next-->Next-->src/main/webapp-->Finish

####3.更改工程文件

>分别找到1中克隆的仓库地址和2中新建工程的工程地址，执行如下命令：

	//最好先删除工程目录下的src文件夹
	进入2工程文件夹下-->删除src文件夹-->拷贝1仓库下的（src,.gitignore,build.gradle,Project Build.md,README.md,WebSocket Support.md）文件到工程文件夹下

####4.构建项目管理

>回到刚刚创建项目的IDE窗口，执行如下命令：

	//本例的基础是STS配置过gradle,git
	选中项目-->刷新项目-->选中项目-->右键Configure-->Convert to Gradle Project-->wait-->gradle会自动下载依赖包-->构建完成

####5.sleep(1000 * 60 * 2)

>当然此步骤可选，至此，完整的工程就构建好了，你会在项目中发现这篇帮助手册

####6.初始化该项目git仓库

>这里主要是建一些本地到远程仓库的分支的映射以及新建自己的分支（以下是基于本人内部仓库的操作，根据个人情况操作）

	//切到git命令窗口，切到工程下的.git仓库，地址根据自己的，如本人在e:/space/xmkf：
	cd e:/space/xmkf
	//进入git仓库后, 查看仓库中分支情况
	git branch -a
	//新建本地develop分支映射远程develop分支
	git checkout -b develop origin/develop
	//根据需求新建自己本地分支，注意执行新建分支时所在的当前分支，这关系到父子关系的确立，比方分支A-->分支B，分支B的基础将是分支A（有关git基础请自行查阅）
	git checkout -b feature/damon

>总结：至此，项目构建，初始仓库等都告一段落，期待您即将对工程所做的贡献。
	