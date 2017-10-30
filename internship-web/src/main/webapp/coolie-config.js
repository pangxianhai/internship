/**
 * ======================================================
 * coolie.js 配置文件 `coolie-config.js`
 * 使用 `coolie.init -j` 生成 `coolie-config.js` 文件模板
 * 前端模块加载器配置文件
 * @link http://coolie.ydr.me/begin/coolie-config.js/
 * @author ydr.me
 * ======================================================
 */

'use strict';


// coolie 配置
coolie.config({
    // 是否在构建之前清空目标目录
    clean: true,

    // js 构建
    js: {
        // 入口模块
        main: [
            '/static/js/app/**/*'
        ],
        // coolie-config.js 路径
        'coolie-config.js': './static/js/coolie-config.js',
        // js 文件保存目录
        dest: './static/js/',
        // 分块配置
        chunk: [
            "./static/js/lib/**/*"
        ]
    },

    // html 构建
    html: {
        // html 文件
        src: [
            '/WEB-INF/views/**'
        ],
        // 是否压缩
        minify: true
    },

    // css 构建
    css: {
        // css 文件保存目录
        dest: './static/css/',
        // css 压缩配置
        minify: {
            compatibility: 'ie7'
        }
    },

    // 资源
    resource: {
        // 资源保存目录
        dest: '/static/images',
        // 是否压缩
        minify: true
    },

    // 原样复制文件
    copy: [
        "/static/images/favicon.ico",
        "/static/js/lib/ueditor/*/**",
        "/static/js/lib/ueditor/*",
        "/static/js/lib/My97DatePicker/*/**",
        "/static/js/lib/My97DatePicker/*"
    ],

    // 目标配置
    dest: {
        // 目标目录
        dirname: '../pro/',
        // 目标根域
        host: '/',
        // 版本号长度
        versionLength: 16
    }
});