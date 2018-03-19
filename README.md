[前端地址](https://github.com/Clairezyw/book-demo)

# BookSystem

### 技术栈
- spring-boot
- mybatis
- spring-mvc

### 功能
- 用户注册登录
- 电子书上传下载
- 评论打分
- coding。。。

### Developer
Stalary Hawk

### 接口文档

### UserController
1. 注册 [post]/user/register
* POST
```
{
	"username": "20150665151",
	"password": "li197910",
	"nickname": "stalary",
	"mail": "stalary@163.com"
}
```
* RETURN
```
{
    "code": 0,
    "msg": "success",
    "description": null,
    "data": "注册成功"
}
```
2. 登录 [post]/user/login 
* POST
```
{
	"username": "20150665113",
	"password": "li197910"
}
```
* RETURN
```
{
    "code": 0,
    "msg": "success",
    "description": null,
    "data": "登录成功"
}
```
3. 修改密码 [PUT]/user/update
* PUT
```
{
	"username": "20150665113",
	"mail": "stalary@163.com",
	"password": "li197910"
}
```
*RETURN
```
{
    "code": 0,
    "msg": "success",
    "description": null,
    "data": "修改成功"
}
```
4. 获取用户信息 [GET]/user/info?id=1
* id不传则为用户获取个人信息
* RETURN
```
{
    "code": 0,
    "msg": "success",
    "description": null,
    "data": {
        "username": "20150665113",
        "nickname": "stalary",
        "password": "D09C794A0DC8D976A6C9CEC54F56F60E",
        "salt": "2f398",
        "mail": "stalary@163.com",
        "id": 1,
        "createTime": 1519104190000,
        "updateTime": 1519104190000,
        "status": 0
    }
}
```
5. 退出 [GET] /user/logout
* RETURN
```
{
    "code": 0,
    "msg": "success",
    "description": null,
    "data": "退出成功"
}
```

#### BookController

1. 上传图书 [POST] /book/books 
* 异步上传图书
* POST
上传的文件，图书名(不填默认文件名)
* RETURN
```
{
    "code": 0,
    "msg": "success",
    "description": null,
    "data": "上传成功！"
}
```

2. 获取图书列表 [GET] /book/books
* RETURN
```
{
    "code": 0,
    "msg": "success",
    "description": null,
    "data": [
        {
            "id": 1,
            "bookName": "《图解HTTP》完整彩色版",
            "nickname": "stalary",
            "coverUrl": "http://p44lruo4o.bkt.clouddn.com/cover:d5f99.jpg",
            "pdfUrl": "http://p44lruo4o.bkt.clouddn.com/book:2cf68.pdf",
            "downloadCount": 0,
            "score": 0,
            "createTime": "2018-02-20 13:40:04"
        }
    ]
}
```

3. 下载图书，获取url [GET] /book/books/1
* RETURN
```
{
    "code": 0,
    "msg": "success",
    "description": null,
    "data": "http://p44lruo4o.bkt.clouddn.com/book:2cf68.pdf"
}
```

4. 获取一本图书详细信息 [GET] /book/books/1/info
* RETURN
```
{
    "code": 0,
    "msg": "success",
    "description": null,
    "data": {
        "book": {
            "id": 1,
            "bookName": "《图解HTTP》完整彩色版",
            "nickname": "stalary",
            "coverUrl": "http://p44lruo4o.bkt.clouddn.com/cover:d5f99.jpg",
            "pdfUrl": "http://p44lruo4o.bkt.clouddn.com/book:2cf68.pdf",
            "downloadCount": 1,
            "score": 3,
            "createTime": "2018-02-20 13:40:04"
        },
        "comments": [
            {
                "id": 1,
                "content": "这本书还不错，很生动形象，但我可能太笨，没怎么看懂",
                "score": 3,
                "nickname": "stalary",
                "type": "中评",
                "createTime": "2018-02-20 13:49:02"
            }
        ]
    }
}
```

5. 查找当前用户上传的图书 [GET] /book/user/books
* RETURN
```
{
    "code": 0,
    "msg": "success",
    "description": null,
    "data": [
        {
            "id": 1,
            "bookName": "《图解HTTP》完整彩色版",
            "nickname": "stalary",
            "coverUrl": "http://p44lruo4o.bkt.clouddn.com/cover:d5f99.jpg",
            "pdfUrl": "http://p44lruo4o.bkt.clouddn.com/book:2cf68.pdf",
            "downloadCount": 1,
            "score": 0,
            "createTime": "2018-02-20 13:40:04"
        }
    ]
}
```

6. 删除一本图书 [DELETE] /book/books/1
* RETURN
```
{
    "code": 0,
    "msg": "success",
    "description": null,
    "data": "删除成功"
}
```

#### CommentController

1. 发表评论 [POST] /comment/comments
* POST
```
{
	"content": "这本书还不错，很生动形象，但我可能太笨，没怎么看懂",
	"score": "3",
	"bookId": 1
}
```
* RETURN
```
{
    "code": 0,
    "msg": "success",
    "description": null,
    "data": "评论成功！"
}
```
