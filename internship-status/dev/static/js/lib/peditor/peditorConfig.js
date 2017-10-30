define(function (require, exports, module) {
    var defaultOptions = {
        width: '100px',
        height: '200px',
        toolbar: [
            'fontFamily', 'fontSize', 'bold', 'italic', 'underline', 'foreColor'
        ],
        // toolbar: [
        //     'fontFamily', 'fontSize', 'bold', 'italic', 'underline', 'foreColor', 'backColor',
        //     'insertorderedlist', 'insertunorderedlist | ',
        //     'justifyleft', 'justifycenter', 'justifyright |',
        //     'source | image ', 'insertvideo', 'fullscreen'
        // ]
        fontFamily: [
            {name: '宋体', val: '宋体,SimSun'},
            {name: '微软雅黑', val: '微软雅黑,Microsoft YaHei'},
            {name: '楷体', val: '楷体,楷体_GB2312, SimKai'},
            {name: '黑体', val: '黑体, SimHei'},
            {name: '隶书', val: '隶书, SimLi'},
            {name: 'andaleMono', val: 'andale mono'},
            {name: 'arial', val: 'arial, helvetica,sans-serif'},
            {name: 'arialBlack', val: 'arial black,avant garde'},
            {name: 'comicSansMs', val: 'comic sans ms'},
            {name: 'impact', val: 'impact,chicago'},
            {name: 'timesNewRoman', val: 'times new roman'},
            {name: 'sans-serif', val: 'sans-serif'}
        ],
        fontSize: [
            {name: '10', val: '10'},
            {name: '12', val: '12'},
            {name: '16', val: '16'},
            {name: '18', val: '18'},
            {name: '24', val: '24'},
            {name: '32', val: '32'},
            {name: '48', val: '48'}
        ]

    };
    return defaultOptions;
});


// define(function (require, exports, module) {
//     var EDITOR_CONFIG = {
//         fontFamily: [
//             {name: 'songti', val: '宋体,SimSun'},
//             {name: 'yahei', val: '微软雅黑,Microsoft YaHei'},
//             {name: 'kaiti', val: '楷体,楷体_GB2312, SimKai'},
//             {name: 'heiti', val: '黑体, SimHei'},
//             {name: 'lishu', val: '隶书, SimLi'},
//             {name: 'andaleMono', val: 'andale mono'},
//             {name: 'arial', val: 'arial, helvetica,sans-serif'},
//             {name: 'arialBlack', val: 'arial black,avant garde'},
//             {name: 'comicSansMs', val: 'comic sans ms'},
//             {name: 'impact', val: 'impact,chicago'},
//             {name: 'timesNewRoman', val: 'times new roman'},
//             {name: 'sans-serif', val: 'sans-serif'}
//         ],
//         fontSize: [
//             {name: '10', val: '10'},
//             {name: '12', val: '12'},
//             {name: '16', val: '16'},
//             {name: '18', val: '18'},
//             {name: '24', val: '24'},
//             {name: '32', val: '32'},
//             {name: '48', val: '48'}
//         ]
//     }
// });