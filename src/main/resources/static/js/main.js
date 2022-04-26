/**
投稿を非同期通信にする関数
 */
function submit() {

	const btn = document.getElementById('send');
	const text = document.getElementById('text');
	// 送信ボタンを押したときに非同期処理が走るようにする
	btn.addEventListener('click', (e) => {
		// フォームに入力された値を取得する
		const formData = new FormData(document.getElementById('form'));
		// XMLHttpRequestオブジェクトの取得
		const xhr = new XMLHttpRequest();
		// 非同期処理をするURLを指定
		xhr.open("POST", "/comfirm", true);
		// 非同期処理のレスポンスタイプの指定
		xhr.responseType = "json";
		// サーバにリクエストを送信する(open()メソッドで指定したリクエスト)
		xhr.send(formData);

		// リクエストの受信に成功したときに実行される処理
		xhr.onload = () => {
			// HTTPステータスコードが200以外の場合
			if (xhr.status != 200) {
				// エラーメッセージを表示する
				alert(`Error ${xhr.status}: ${xhr.statusText}`);
				return null;
			}

			if (text.value === '') {
				alert('投稿に失敗しました');
			} else if (text.value !== '') {
				// 投稿成功時にHTMLを書き換える
				const container = document.getElementById('row');
				const html = `
				<div class="contents row">
  					<div class="success">
    					<h3>投稿が完了しました。</h3>
    					<a class="btn" href=/index>投稿一覧へ戻る</a>
  					</div>
				</div>`
				container.innerHTML = html;
			}
		}
		// イベントの初期化(二重でデータを送信することを防ぐ)
		e.preventDefault();
	});
};

// ページが読み込まれたときに上記の関数を呼び出す
window.addEventListener('load', submit);