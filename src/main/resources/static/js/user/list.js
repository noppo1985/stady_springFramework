'use strict'

var userData = null; //ユーザーﾃﾞ^ﾀ
var table = null;　//DataTables オブジェクト

/**画面ロード時の処理 */

jQuery(function($){
	
	//DataTablesの初期化
	createDataTables();
	//**検索ボタンを押したときの処理 */
	$('#btn-search').click(function(event){
		//検索
		search();
	});
});

/**検索処理. */

function search(){
	//form の値を取得
	let formData = $('#user-search-form').serialize();

	//ajax勇信
	$.ajax({
		type : "GET",
		url : '/user/get/list',
		data : formData,
		dataType : 'json',
		contentType : 'application/json;charset=UTF-8',
		cache : false,
		timeout : 5000,
		
	}).done(function(data){
		
		//ajax成功時の処理
		console.log(data);
		
		//JSONを　変数に入れる。
		
		userData = data;
		
		// DataTables作成
		
		createDataTables();
		
	}).fail(function(jqXHR,textStatus,errorThrown){
		//ajax失敗時の処理
		
		alert('検索処理に失敗しました');
		
	}).always(function(){
		//常に実行する処理
		
	});
	
}

/**DataTables作成. */

function createDataTables(){
	//すでにDataTableが作成されている場合
	if(table != null){
		
		table.destroy();
	}
	
	//DataTables
	table = $('#user-list-table').DataTable({
		//日本語化
		language:{
			url: '/webjars/datatables-plugins/i18n/Japanese.json/'
			
		},
		//データのセット
		data:userData,
		columns:[
			{data:'userId'},
			{data:'userName'},
			{data:'birthday', //誕生日
				render:function(data,type,row){
					let date = new Date(data);
					let year = date.getFullYear();
					let month = date.getMonth() + 1;
					date = date.getDate();
					return year + '/' + month + '/' + date ;
				}			
			},
			{data:'age'},//年齢
			{
				data:'gender'
				,render:function(data,type,row){
					let gender = '';
					if(data === 1){
						gender = '男性';						
					}else{
						gender = '女性';
					}
				return gender;
				}
			},
			{
				data:'userId',//詳細画面のURL
				render: function(data,type,row){
					let url = '<a href="/user/detail/' + data + '"> 詳細　</a>'
					return url;				}
			},
		]
	})
	
}