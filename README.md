# PigeonTranscoding
---
#### 数据库转码
>Akkain的Pigeon日记系统不明原因出现数据库乱码（应该是我服务器问题），做了一个小工具，可以转码，（windows-1252转UTF8）
Pigeon系统GITHUB地址：https://github.com/kasuganosoras/Pigeon

使用方法比较简单，代码没有技术含量，功能直接堆上去的，拿来改改就能用。
目录下的sql.properties文件是数据库配置文件，用的时候记得更改，
`WINToUTF.java`文件的第55行，SQL语句根据自己实际情况更改，我的是ID103 104 105没问题，所以写了WHERE id!=103 AND id!=104 AND id!=105

---

用Java自带的功能实现文字编码转换试了一下，不能从win1252完美转换UTF8，所以百度了一个网站，通过JSOUP POST了数据到网站，做了一些正则匹配处理。如果有其他需要可以自己DIY。
（网站地址：http://www.mytju.com/classCode/tools/messyCodeRecover.asp ）

---

SQLUtils那个类是瞎写的，凑活着用吧，awa

---

我的Pigeon地址：https://blog.mcplugin.cn/pigeon
