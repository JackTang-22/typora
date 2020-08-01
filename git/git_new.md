# git本地项目上传到github步骤

1. mkdir file    创建目录
2. cd file     进入目录
3. git init   初始化git项目
4. touch .gitignore 新建文件，将不需要上传到github的文件和目录信息在该文件中指定
5. git add . 添加项目到仓库
6. git commit -m "注释" 提交项目到仓库
7. 进入github, 创建新的仓库 file
8. git remote add origin git@github.com:xxx/file.git  本地仓库和远程仓库关联
9. git push -u origin master   提交项目到远程仓库
10. 如果github项目初始化的时候创建了README文件，执行git pull --rebase origin master,然后在提交即可

