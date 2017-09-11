package com.cn.hnust.kotlinPro.bean

/**
 *语言数据实体
 */
data class LanguageData(var languageId:String,var languageType:String )

/**
 *批量收听获取的数据实体
 */
data class BatchDataFetch(var historicalRelicId:String,
						  var museumId:String,
						  var historicalRelicName:String,
						  var level:String,
						  var dynasty:String,
						  var pictureAddress:String)

data class PurchaseRecord(var buyTime:String,
							var historicalRelicId:String,
							var historicalRelicName:String,
							var level:String,
							var dynasty:String,
							var museumName:String,
							var pictureAddress:String,
							var listen:String,
							var museumId:String,
							var languageId:String)