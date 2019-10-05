'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

/**
 * webpack.dev.conf.js的plugins配置会读取NODE_ENV，
 * 并将此局部变量设置为全局变量,
 * 可通过process.env.NODE_ENV获取
 */
module.exports = merge(prodEnv, {
  NODE_ENV: '"development"'
})
