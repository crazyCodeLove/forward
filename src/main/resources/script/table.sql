CREATE TABLE `route` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `system_name` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '系统名称',
  `system_sign` varchar(256) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '系统签名',
  `pattern` varchar(256) COLLATE utf8mb4_bin NOT NULL COMMENT '匹配规则',
  `des_uri` varchar(256) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '目标URI',
  `status` tinyint(4) NOT NULL COMMENT '状态，1：启用，0：禁用',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;