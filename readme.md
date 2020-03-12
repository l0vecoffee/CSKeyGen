#CSKeyGen参数说明

##-r <加密后的授权文件> <公钥文件> 

用于读取CS加密后的授权文件
> 如-r cobaltstrike.auth authkey.pub显示结果如下
>
- licensekey:	9ab7-fbe1-d915-b858
- valid to:	九月 8, 2019
- watermark:	1873433027
- Issued at:	12/31 03:27:45

##-g <私钥文件> <加密后的授权文件>

用于通过私钥加密授权内容，生成授权文件
>如 -g private.key cobaltstrike.auth 通过提供的private.key生成cobaltstrike.auth文件

## -k <私钥文件> <公钥文件>

用于生成私钥和公钥文件

##免责声明：
本代码只是为了研究cs的验证授权部分，请勿用于其他用途。