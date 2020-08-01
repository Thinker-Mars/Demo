import BasicLoader from "./basicLoader";
import IELoader from "./ieLoader";

function SmartReader(input) {
    this._input = input; // 文件所在的Element对象
    this._loader = undefined; // 文件加载器
}

SmartReader.prototype.start = function() {
    var that = this;
    return new Promise(function(resolve, reject) {
        if (!that._loader) {
            that.initLoader();
        }
        if (!that._loader) {
            /** 暂无合适的loader，直接返回空数组 */
            var re = [];
            resolve(re);
        }
        that._loader.parse(that._input).then(res => {
            resolve(res);
        });
    })
}

/**
 * 根据宿主环境初始化loader
 */
SmartReader.prototype.initLoader = function() {
    if (window.FileReader) {
        this.use(new BasicLoader());
    } else if (window.ActiveXObject) {
        this.use(new IELoader());
    }
}


/**
 * 加载loader，目前已有支持主流浏览器的BasicLoader，以及支持IE的IELoader
 */
SmartReader.prototype.use = function(loader) {
    this._loader = loader;
}

export default SmartReader;



/**
 * 文件读取插件介绍：
 *  
 * 输入：input元素的Element
 * 输出：根据loader定义，目前为文件内容数组
 * 
 * 
 * 工作流程：
 * 
 *                                                 loader处理逻辑
 *                                           --------------------------
 *               ---------------             |1、判断文件类型           |
 * file----------|   根据环境   | ----------  | 2、选择不同规则处理文件  |------------out
 *               |  选择loader  |             | 3、输出处理结果         |
 *               ---------------              -------------------------
 * 
 * usage:
 * 1.实例化
 *  var reader = new SmartReader(input);
 * 2.启动插件，获取结果
 *  reader.start().then(res => {
 *      ...
 *  //res便是解析结果
 * })
 * 
 */