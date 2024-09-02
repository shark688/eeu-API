export default [
  { path: '/', name: '主页', icon: 'smile', component: './Index' },
  { path: '/interfaceInfo/:id', name: '接口详情', icon: 'smile', component: './InterfaceInfo',hideInMenu: true },
  {
    path: '/user',
    layout: false,
    routes: [{ name: '登录', path: '/user/login', component: './User/Login' },{ name: '注册', path: '/user/register', component: './User/Register' }],
  },
  //{ path: '/welcome', name: '欢迎', icon: 'smile', component: './Index' },
  {
    path: '/admin',
    name: '管理页',
    icon: 'crown',
    access: 'canAdmin',
    routes: [
      { name: '查询表格', icon: 'table', path: '/admin/interface_info', component: './Admin/InterfaceInfo' },
    ],
  },
  { path: '*', layout: false, component: './404' },
];
