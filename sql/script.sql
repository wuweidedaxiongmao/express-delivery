create table address
(
    id         int auto_increment comment '地址ID'
        primary key,
    user_id    int          null comment '用户ID（指向student表的id）',
    address    varchar(255) null comment '详细地址',
    created_at datetime     null comment '创建时间',
    updated_at datetime     null comment '更新时间'
)
    comment '用户地址信息表(快递送达地址)';

create table admin
(
    id         int auto_increment comment '管理员ID'
        primary key,
    username   varchar(255) not null comment '用户名',
    password   varchar(255) not null comment '密码',
    name       varchar(255) null comment '姓名',
    avatar     varchar(255) null comment '头像链接',
    role       varchar(255) null comment '角色',
    phone      varchar(255) null comment '电话号码',
    email      varchar(255) null comment '电子邮箱',
    created_at datetime     null comment '创建时间',
    updated_at datetime     null comment '更新时间'
)
    comment '管理员信息表';

create table cash_record
(
    id         int auto_increment comment '记录ID'
        primary key,
    courier_id int            null comment '代取员ID（指向student表的id）',
    amount     decimal(10, 2) null comment '提现金额',
    status     varchar(50)    null comment '状态（待审核/已通过/已拒绝）',
    admin_id   int            null comment '审核管理员ID',
    created_at datetime       null comment '申请时间',
    updated_at datetime       null comment '更新时间（审核时间）'
)
    comment '提现记录/提现申请表';

create table courier_commission
(
    id              int auto_increment comment '等级ID'
        primary key,
    courier_level   int          null comment '代取员等级（例如1、2、3）',
    commission_rate double(5, 2) null comment '提现比例（百分比，例如0.7表示70%）',
    info            varchar(255) null comment '对等级的说明',
    created_at      datetime     null comment '创建时间',
    updated_at      datetime     null comment '更新时间'
)
    comment '代取员等级信息表';

create table express_type
(
    id          int auto_increment comment '分类ID'
        primary key,
    type_name   varchar(255) null comment '分类名称（例如普通快递/加急快递）',
    price       int          null comment '分类价格',
    description text         null comment '分类描述',
    created_at  datetime     null comment '创建时间',
    updated_at  datetime     null comment '更新时间'
)
    comment '代取快递分类表';

create table feedback
(
    id         int auto_increment comment '反馈ID'
        primary key,
    user_id    int         null comment '用户ID（指向student表的id）',
    user_type  varchar(50) null comment '用户类型（student/courier）',
    content    text        null comment '反馈内容',
    status     varchar(50) null comment '状态（待处理/已解决）',
    created_at datetime    null comment '提交时间',
    updated_at datetime    null comment '更新时间（处理时间）'
)
    comment '反馈信息表';

create table identification
(
    id         int auto_increment comment '申请ID'
        primary key,
    student_id int          null comment '学生ID（指向student表的id）',
    name       varchar(255) null comment '姓名：通过name进行模糊查询',
    image      varchar(255) null comment '存放学生证图片的路径',
    status     varchar(50)  null comment '状态（待审核/已通过/已拒绝）',
    admin_id   int          null comment '审核管理员ID',
    created_at datetime     null comment '申请时间',
    updated_at datetime     null comment '更新时间（审核时间）'
)
    comment '代取员认证申请表';

create table information
(
    id         int auto_increment comment '资讯ID'
        primary key,
    title      varchar(255) null comment '标题',
    content    text         null comment '内容',
    type       varchar(50)  null comment '类型（资讯/公告）',
    created_at datetime     null comment '创建时间',
    updated_at datetime     null comment '更新时间'
)
    comment '校园资讯/公告信息表';

create table notice
(
    id         int auto_increment comment '资讯ID'
        primary key,
    title      varchar(255) null comment '标题',
    content    text         null comment '内容',
    type       varchar(50)  null comment '类型（资讯/公告）',
    created_at datetime     null comment '创建时间',
    updated_at datetime     null comment '更新时间'
)
    comment '公告信息表';

create table orders
(
    id             int auto_increment comment '订单ID'
        primary key,
    student_id     int          null comment '学生ID（指向student表的id）',
    courier_id     int          null comment '代取员ID（指向student表的id，可为空）',
    type_id        int          null comment '代取类型ID（关联express_type）',
    pickup_address varchar(255) null comment '取快递地址',
    address_id     int          null comment '地址ID，快递送达地址',
    image          varchar(255) null comment '快递位置图片',
    description    varchar(255) null comment '订单描述',
    status         varchar(50)  null comment '状态（待支付/待接单/已接单，派送中/已送达/已签收）',
    create_time    datetime     null comment '创建时间',
    access_time    datetime     null comment '订单接收时间',
    delivery_time  datetime     null comment '订单送达时间',
    sign_time      datetime     null comment '订单签收时间',
    rating         double(5, 3) null comment '评分(保留三位小数)',
    feedback       varchar(255) null comment '反馈信息'
)
    comment '代取订单信息表';

create table release_apply
(
    id         int auto_increment comment '申请ID'
        primary key,
    courier_id int         null comment '代取员ID（指向student表的id）',
    reason     text        null comment '解封原因',
    status     varchar(50) null comment '状态（待审核/已通过/已拒绝）',
    admin_id   int         null comment '审核管理员ID',
    created_at datetime    null comment '申请时间',
    updated_at datetime    null comment '更新时间（审核时间）'
)
    comment '代取员解封申请表';

create table student
(
    id           int auto_increment comment '学生ID'
        primary key,
    username     varchar(255) not null comment '用户名',
    password     varchar(255) not null comment '密码',
    name         varchar(255) null comment '姓名',
    avatar       varchar(255) null comment '头像链接',
    phone        varchar(255) null comment '电话号码',
    email        varchar(255) null comment '电子邮箱',
    role         varchar(50)  null comment '角色（student/courier）',
    money        int          null comment '余额(指用户充值的钱，能够按100%比例体现)',
    add_money    int          null comment '代取员赚的钱(仅对代取员有效，体现比例需要按照代取员等级)',
    level        int          null comment '代取员等级（例如1、2、3，仅对代取员有效）',
    rating       double(5, 3) null comment '平均评分（基于订单，仅对代取员有效）',
    rating_count int          null comment '用于记录评分订单数来计算平均评分',
    order_count  int          null comment '接单数量（用于排行榜，仅对代取员有效）',
    created_at   datetime     null comment '注册时间',
    updated_at   datetime     null comment '更新时间'
)
    comment '学生信息表（包含代取员信息）';

create table upgrade_apply
(
    id            int auto_increment comment '申请ID'
        primary key,
    courier_id    int         null comment '代取员ID（指向student表的id）',
    current_level int         null comment '当前等级',
    target_level  int         null comment '目标等级',
    status        varchar(50) null comment '状态（待审核/已通过/已拒绝）',
    admin_id      int         null comment '审核管理员ID',
    created_at    datetime    null comment '申请时间',
    updated_at    datetime    null comment '更新时间（审核时间）'
)
    comment '代取员提升等级申请表';
