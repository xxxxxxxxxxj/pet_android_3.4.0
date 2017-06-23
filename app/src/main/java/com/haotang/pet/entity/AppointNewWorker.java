package com.haotang.pet.entity;

import java.io.Serializable;
import java.util.List;

public class AppointNewWorker implements Serializable {
	private static final long serialVersionUID = 11243223L;
	private int code;
	private DataBean data;
	private String msg;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public DataBean getData() {
		return data;
	}

	public void setData(DataBean data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public static class DataBean implements Serializable {
		private static final long serialVersionUID = 1111111L;
		private List<WorkersBean> workers;
		private List<ServicesBean> services;

		public List<ServicesBean> getServices() {
			return services;
		}

		public void setServices(List<ServicesBean> services) {
			this.services = services;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		public List<WorkersBean> getWorkers() {
			return workers;
		}

		public void setWorkers(List<WorkersBean> workers) {
			this.workers = workers;
		}

		public static class ServicesBean implements Serializable {
			private static final long serialVersionUID = 5555555L;
			private int serviceId;
			private List<LevelsBean> levels;

			public int getServiceId() {
				return serviceId;
			}

			public void setServiceId(int serviceId) {
				this.serviceId = serviceId;
			}

			public List<LevelsBean> getLevels() {
				return levels;
			}

			public void setLevels(List<LevelsBean> levels) {
				this.levels = levels;
			}

			public static long getSerialversionuid() {
				return serialVersionUID;
			}

			public static class LevelsBean implements Serializable {
				private static final long serialVersionUID = 6666666L;
				private String desc;
				private int level;
				private String tip;
				private String title;

				public String getDesc() {
					return desc;
				}

				public void setDesc(String desc) {
					this.desc = desc;
				}

				public int getLevel() {
					return level;
				}

				public void setLevel(int level) {
					this.level = level;
				}

				public String getTip() {
					return tip;
				}

				public void setTip(String tip) {
					this.tip = tip;
				}

				public String getTitle() {
					return title;
				}

				public void setTitle(String title) {
					this.title = title;
				}

				public static long getSerialversionuid() {
					return serialVersionUID;
				}
			}
		}

		public static class WorkersBean implements Serializable {
			private static final long serialVersionUID = 2222222L;
			private LevelBean level;
			private List<Workers> workers;

			public LevelBean getLevel() {
				return level;
			}

			public void setLevel(LevelBean level) {
				this.level = level;
			}

			public List<Workers> getWorkers() {
				return workers;
			}

			public void setWorkers(List<Workers> workers) {
				this.workers = workers;
			}

			public static class LevelBean implements Serializable {
				private static final long serialVersionUID = 3333333L;
				private int level;
				private String name;

				public String getName() {
					return name;
				}

				public void setName(String name) {
					this.name = name;
				}

				public static long getSerialversionuid() {
					return serialVersionUID;
				}

				public int getLevel() {
					return level;
				}

				public void setLevel(int level) {
					this.level = level;
				}
			}

			public static class Workers implements Serializable {
				private static final long serialVersionUID = 16987L;
				private int workerId;
				private int isAvail;
				private String avatar;
				private String realname;
				private List<PricesBean> prices;
				private String desc_bottom;
				private String levelName;

				public String getLevelName() {
					return levelName;
				}

				public void setLevelName(String levelName) {
					this.levelName = levelName;
				}

				public static long getSerialversionuid() {
					return serialVersionUID;
				}

				public String getDesc_bottom() {
					return desc_bottom;
				}

				public void setDesc_bottom(String desc_bottom) {
					this.desc_bottom = desc_bottom;
				}

				public int getWorkerId() {
					return workerId;
				}

				public void setWorkerId(int workerId) {
					this.workerId = workerId;
				}

				public int getIsAvail() {
					return isAvail;
				}

				public void setIsAvail(int isAvail) {
					this.isAvail = isAvail;
				}

				public String getAvatar() {
					return avatar;
				}

				public void setAvatar(String avatar) {
					this.avatar = avatar;
				}

				public String getRealname() {
					return realname;
				}

				public void setRealname(String realname) {
					this.realname = realname;
				}

				public List<PricesBean> getPrices() {
					return prices;
				}

				public void setPrices(List<PricesBean> prices) {
					this.prices = prices;
				}

				public static class PricesBean implements Serializable {
					private static final long serialVersionUID = 444444L;
					private int petId;
					private int price;

					public int getPetId() {
						return petId;
					}

					public void setPetId(int petId) {
						this.petId = petId;
					}

					public int getPrice() {
						return price;
					}

					public void setPrice(int price) {
						this.price = price;
					}
				}
			}
		}
	}
}
