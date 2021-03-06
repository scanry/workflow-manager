use workflow_manager;
CREATE TABLE `job_model` (
  `id` varchar(36) NOT NULL COMMENT '数据id:业务无关性',
  `code` varchar(10) NOT NULL COMMENT '编码',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `create_role_id` varchar(20) NOT NULL COMMENT '该流程作业模型作业的角色',
  `remarks` varchar(100) DEFAULT NULL COMMENT '数据备注',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '数据版本',
  `update_user_id` varchar(36) NOT NULL COMMENT '数据更新用户id',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据更新日期id',
  `create_user_id` varchar(36) NOT NULL COMMENT '数据创建用户id',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `job_model_uk1` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4 COMMENT='流程作业模型表';

CREATE TABLE `task_model` (
  `id` varchar(36) NOT NULL COMMENT '数据id:业务无关性',
  `name` varchar(20) NOT NULL COMMENT '流程作业模型名称',
  `job_id` varchar(36) NOT NULL COMMENT '流程作业模型id',
  `phase` int(4) NOT NULL COMMENT '流程阶段',
  `type` varchar(20) NOT NULL COMMENT '任务类型',
  `worker` varchar(100) NOT NULL COMMENT '任务工人,当类型为自动时，该字段表示java class.否则为处理角色',
  `remarks` varchar(100) DEFAULT NULL COMMENT '数据备注',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '数据版本',
  `update_user_id` varchar(36) NOT NULL COMMENT '数据更新用户id',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据更新日期id',
  `create_user_id` varchar(36) NOT NULL COMMENT '数据创建用户id',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `job_phase_UNIQUE` (`job_id`,`phase`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程任务模型表';

CREATE TABLE `subprocess_model` (
  `id` varchar(36) NOT NULL COMMENT '数据id:业务无关性',
  `task_id` varchar(20) NOT NULL COMMENT '流程作业模型名称',
  `job_id` varchar(36) NOT NULL COMMENT '流程作业模型id',
  `remarks` varchar(100) DEFAULT NULL COMMENT '数据备注',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '数据版本',
  `update_user_id` varchar(36) NOT NULL COMMENT '数据更新用户id',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据更新日期id',
  `create_user_id` varchar(36) NOT NULL COMMENT '数据创建用户id',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `subprocess_model_uk1` (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='子处理模型表';

CREATE TABLE `worker_model` (
  `id` varchar(36) NOT NULL COMMENT '数据id:业务无关性',
  `name` varchar(20) NOT NULL COMMENT '任务worker名称',
  `job_codes` varchar(100) NOT NULL COMMENT '处理的流程job编码集合,以分号(;)分割',
  `phases` varchar(100) NOT NULL COMMENT '处理的流程job阶段集合,以分号(;)分割',
  `check_interval` int(11) NOT NULL COMMENT '定时检查间隔',
  `script_type` varchar(20) NOT NULL,
  `script` varchar(200) NOT NULL COMMENT '远程url',
  `remark` varchar(100) DEFAULT NULL COMMENT '数据备注',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '数据版本',
  `update_user_id` varchar(36) NOT NULL COMMENT '数据更新用户id',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据更新日期id',
  `create_user_id` varchar(36) NOT NULL COMMENT '数据创建用户id',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程任务worker表';

CREATE TABLE `event_model` (
  `id` varchar(36) NOT NULL COMMENT '数据id:业务无关性',
  `task_id` varchar(20) NOT NULL COMMENT '流程任务模型id',
  `type` varchar(20) NOT NULL COMMENT '事件类型',
  `execute_script` int(11) NOT NULL COMMENT '事件执行脚本',
  `remarks` varchar(100) DEFAULT NULL COMMENT '数据备注',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '数据版本',
  `update_user_id` varchar(36) NOT NULL COMMENT '数据更新用户id',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据更新日期id',
  `create_user_id` varchar(36) NOT NULL COMMENT '数据创建用户id',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程事件模型表';

CREATE TABLE `job` (
  `id` varchar(36) NOT NULL COMMENT '数据id:业务无关性',
  `name` varchar(20) NOT NULL COMMENT '流程作业名称',
  `model_id` varchar(36) NOT NULL COMMENT '流程作业模型id',
  `status` varchar(20) NOT NULL COMMENT '状态',
  `current_task_id` varchar(20) NOT NULL COMMENT '当前任务id',
  `current_phase` varchar(20) NOT NULL COMMENT '当前流程阶段',
  `lock_url` varchar(200) DEFAULT NULL COMMENT '锁着job的服务url',
  `remarks` varchar(100) DEFAULT NULL COMMENT '数据备注',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '数据版本',
  `update_user_id` varchar(36) NOT NULL COMMENT '数据更新用户id',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据更新日期id',
  `create_user_id` varchar(36) NOT NULL COMMENT '数据创建用户id',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4 COMMENT='流程作业表';

CREATE TABLE `workflow_task` (
  `id` varchar(36) NOT NULL COMMENT '数据id:业务无关性',
  `name` varchar(20) NOT NULL COMMENT '流程作业模型名称',
  `job_id` varchar(36) NOT NULL COMMENT '流程作业模型id',
  `model_id` varchar(36) NOT NULL COMMENT '流程作业任务模型id',
  `phase` int(11) NOT NULL COMMENT '流程阶段',
  `status` varchar(20) NOT NULL COMMENT '任务状态',
  `type` varchar(20) NOT NULL COMMENT '任务类型',
  `role_id` varchar(36) COMMENT '任务所属角色id,当类型为人工审批时，此字段必须有值',
  `owner_user_id` varchar(20) COMMENT '任务处理用户',
  `worker` varchar(100) COMMENT '任务自动审批工人,当类型为自动审批时，此字段必须有值',
  `remarks` varchar(100) DEFAULT NULL COMMENT '数据备注',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '数据版本',
  `update_user_id` varchar(36) NOT NULL COMMENT '数据更新用户id',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据更新日期id',
  `create_user_id` varchar(36) NOT NULL COMMENT '数据创建用户id',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程任务表';

CREATE TABLE `workflow_task_opinion` (
  `id` varchar(36) NOT NULL COMMENT '数据id:业务无关性',
  `job_id` varchar(36) NOT NULL COMMENT '流程作业id',
  `task_id` varchar(36) NOT NULL COMMENT '流程任务id',
  `inner_opinion` varchar(500) NOT NULL COMMENT '内部意见',
  `outer_opinion` varchar(500) NOT NULL COMMENT '外部意见',
  `remarks` varchar(100) DEFAULT NULL COMMENT '数据备注',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '数据版本',
  `update_user_id` varchar(36) NOT NULL COMMENT '数据更新用户id',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据更新日期id',
  `create_user_id` varchar(36) NOT NULL COMMENT '数据创建用户id',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程任务意见表';


CREATE TABLE `worker` (
  `id` varchar(36) NOT NULL COMMENT '数据id:业务无关性',
  `rawId` varchar(36) NOT NULL COMMENT '原始数据id',
  `rawVersion` int(11) NOT NULL COMMENT '原始数据版本',
  `name` varchar(20) NOT NULL COMMENT '任务worker名称',
  `job_codes` varchar(100) NOT NULL COMMENT '处理的流程job编码集合,以分号(;)分割',
  `phases` varchar(100) NOT NULL COMMENT '处理的流程job阶段集合,以分号(;)分割',
  `check_interval` int(11) NOT NULL COMMENT '定时检查间隔',
  `script_type` varchar(20) NOT NULL COMMENT '脚本类型',
  `script` varchar(200) NOT NULL COMMENT '脚本',
  `remark` varchar(100) DEFAULT NULL COMMENT '数据备注',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '数据版本',
  `update_user_id` varchar(36) NOT NULL COMMENT '数据更新用户id',
  `update_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据更新日期id',
  `create_user_id` varchar(36) NOT NULL COMMENT '数据创建用户id',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='流程任务worker表';

