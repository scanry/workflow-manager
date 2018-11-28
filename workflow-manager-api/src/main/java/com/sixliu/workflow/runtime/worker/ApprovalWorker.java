package com.sixliu.workflow.runtime.worker;

import com.sixliu.workflow.runtime.dto.TaskProcessResult;

/**
*@author:MG01867
*@date:2018年11月23日
*@email:359852326@qq.com
*@version:
*@describe //TODO
*/
@FunctionalInterface
public interface ApprovalWorker {

	TaskProcessResult process(String taskId);
}
