/** 一般加载器，支持主流浏览器，以下浏览器测试通过：Chrome、IE11、FireFox */
function BasicLoader() {
    this._engine = undefined;
}

BasicLoader.prototype.parse = function(input) {
    var that = this;
    return new Promise(function(resolve, reject) {
        var file = input.files[0];
        var suffix = that.getFileType(file);
        if (suffix == "txt") {
            that.parseTxt(file).then(res => {
                resolve(res);
            });
        } else if (suffix == "csv") {
					// 暂未完成
					return that.parseCsv();
        } else {
            // 暂不支持的文件解析类型
            var re = [];
            resolve(re);
        }
    })
}

/**
 * 获取文件类型
 */
BasicLoader.prototype.getFileType = function(file) {
    var fileName = file.name;
    var pos = fileName.lastIndexOf(".");
    if (pos != -1) {
        return fileName.slice(pos + 1);
    } else {
        return "NaN";
    }
}

/**
 * 解析txt格式的文件
 * readAsText方法是异步的，只有执行完才能看到结果
 * 必须要在onload或onloadend中处理结果
 * 所以解析方法被封装为一个Promise
 */
BasicLoader.prototype.parseTxt = function(file) {
    var that = this;
    return new Promise(function(resolve, reject) {
        that._engine = new FileReader();
        that._engine.readAsText(file, "UTF-8");
        that._engine.onload = function() {
            var _result = that._engine.result;
            var re = [];
            re = _result.split("\n").filter(function(item) {
                /** 过滤换行符，占一个字节，即空白行 */
                return item.length > 1;
            });
            resolve(re);
        };
    })
}

/**
 * 解析csv格式的文件
 */
BasicLoader.prototype.parseCsv = function() {
    return 2;
}

export default BasicLoader;