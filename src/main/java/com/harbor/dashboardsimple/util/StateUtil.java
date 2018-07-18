package com.harbor.dashboardsimple.util;

/**
 * 状态工具类
 * 
 * @author szy
 *
 */
public class StateUtil {

	/**
	 * 任务进行状态
	 */
	public enum workState { 
		/**
		 * not confim 技术leader待确认、待派遣 C
		 */
		ConfimNot('C'),

		/**
		 * confim yes && dispatch yes && technology to do....
		 * 技术leader已确认、即已派遣、技术待完成 S
		 */
		SuccessNotTodo('S'),

		/**
		 * work return 技术leaer对任务有意见、申请退回 R
		 */
		Return('R'),

		/**
		 * work do Yes 已完成、待审核 Y
		 */
		Yes('Y'),

		/**
		 * work Destory 任务消毁、重新申请 D
		 */
		Destory('D');
		
		private char value;
		 
		// 构造方法
		private workState(char value) {
			this.value = value;
		}

		public char getValue() {
			return value;
		}

		public void setValue(char value) {
			this.value = value;
		}
	}

	/**
	 * 工单进行状态
	 */
	public enum orderState {
		/**
		 * not confim 技术leader已派遣、未接受 C
		 */
		ConfimNot('C'),

		/**
		 * SuccessNot && To do 已接受、待完成 T
		 */
		SuccessNotTodo('S'),

		/**
		 * order return 工单意见不一致、技术申请退回 R
		 */
		Return('R'),

		/**
		 * order do Yes 已完成、待审核 Y
		 */
		Yes('Y'),

		/**
		 * order Destory 工单销售、技术退单给leader、leder经协调后不再重新派遣、继续退给销售、即工单被消毁
		 */
		Destory('D');
		
		private char value;
		 
		// 构造方法
		private orderState(char value) {
			this.value = value;
		}

		public char getValue() {
			return value;
		}

		public void setValue(char value) {
			this.value = value;
		}
	}

	/**
	 * 任务审核状态 && 工单审核状态
	 *
	 */
	public enum checkState {
		/**
		 * wait check 等待审核 W
		 */
		WaitCheck('W'),

		/**
		 * check pass 审核通过 P
		 */
		Pass('P'),

		/**
		 * check fail 审核驳回 F
		 */
		Fail('F'),

		/**
		 * check pass && sale score 审核通过并且销售已评分 S
		 */
		Score('S');
		
		private char value;
		 
		// 构造方法
		private checkState(char value) {
			this.value = value;
		}

		public char getValue() {
			return value;
		}

		public void setValue(char value) {
			this.value = value;
		}
	}

	/**
	 * 流水flag、每次新增流水为New、旧流水置为old
	 *
	 */
	public enum streamFlag {
		/**
		 * 旧流水 O
		 */
		Old('O'),

		/**
		 * 最新流水 N
		 */
		New('N');
		
		private char value;
		 
		// 构造方法
		private streamFlag(char value) {
			this.value = value;
		}

		public char getValue() {
			return value;
		}

		public void setValue(char value) {
			this.value = value;
		}		
	}

	/**
	 * 
	 * 角色枚举
	 */
	public enum uploadRole {
		/**
		 * 销售 S
		 */
		Sale('S'),

		/**
		 * 技术leader L
		 */
		LeaderTechnology('L'),

		/**
		 * 技术 T
		 */
		Technology('T');
		
		private char value;
		 
		// 构造方法
		private uploadRole(char value) {
			this.value = value;
		}

		public char getValue() {
			return value;
		}

		public void setValue(char value) {
			this.value = value;
		}			
	}
	
	/**
	 * 是否外包枚举
	 */
	public enum outSourceFlag{
		/**
		 * 存在外包人员
		 */
		Yes('Y'),

		/**
		 * 不存在外包人员
		 */
		No('N');

		private char value;
		 
		// 构造方法
		private outSourceFlag(char value) {
			this.value = value;
		}

		public char getValue() {
			return value;
		}

		public void setValue(char value) {
			this.value = value;
		}					
	}
}
