/** ie加载器，支持ie7-10 */

function IELoader() {
    this._engine = undefined;
}

IELoader.prototype.parse = function(input) {
    var that = this;
    return new Promise(function(resolve, reject) {
        that._engine = new ActiveXObject("Scripting.FileSystemObject");
        var fileType = that.getFileType(input);
        var re = [];
        if (fileType == "txt") {
            re = that.parseTxt(input);
            resolve(re);
        } else if (fileType == "csv") {
            resolve(re);
        } else {
            // 不支持的文件类型
            resolve(re);
        }
    })
}

/**
 * 获取文件类型
 */
IELoader.prototype.getFileType = function(input) {
    // 暴露文件地址不安全，所以需要用到的地方只声明为局部变量
    var filePath = input.value;
    var fileObj = this._engine.GetFile(filePath);
    var fileName = fileObj.name;
    var pos = fileName.lastIndexOf(".");
    if (pos != -1) {
        return fileName.slice(pos + 1);
    } else {
        return "NaN";
    }
}

IELoader.prototype.parseTxt = function(input) {
    var re = [];
    var filePath = input.value;
    var f = that._engine.opentextfile(filePath, 1, true);
    while (!f.AtEndOfStream) {
        re.push(f.Readline());
    }
    f.close();
    return re;
}


IELoader.prototype.parseCsv = function() {

}

export default IELoader;