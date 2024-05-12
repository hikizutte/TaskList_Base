function validate() {
    //     情報を取得し、変数に入れる
    let task = document.getElementById('task').value;
    let start = document.getElementById('start').value;
    let deadline = document.getElementById('deadline').value;
    let description = document.getElementById('description').value;
    let start_date = Date.parse(start);
    let deadline_date = Date.parse(deadline);
    let task_color = document.getElementById('task');
    let start_color = document.getElementById('start');
    let deadline_color = document.getElementById('deadline');
    let description_color = document.getElementById('description');
    //     resultがtrueの場合の処理
    let result = true;
    document.getElementById('err_msg_task_blank').style.display = '';
    document.getElementById('err_msg_task_character_limit').style.display = '';
    document.getElementById('err_msg_starts_after_deadline').style.display = '';
    document.getElementById('err_msg_description_character_limit').style.display = '';
    task_color.style.borderColor = task_color.style.borderWidth = '';
    start_color.style.borderColor = start_color.style.borderWidth = '';
    deadline_color.style.borderColor = deadline_color.style.borderWidth = '';
    description_color.style.borderColor = description_color.style.borderWidth = '';

     //      タスクタイトルがnullかスペースのみの場合の処理
    if (!task || task.trim().length == 0) {
        //         枠の色付けと太字にしている
        task_color.style.borderColor = 'red';
        task_color.style.borderWidth = '2px';
         エラーメッセージを表示する */
        document.getElementById('err_msg_task_blank').style.display = 'block';
        result = false;
    }

    //      タスクタイトルが31文字以上だった時の処理
    if (task.length >= 31) {
         task_color.style.borderColor = 'red';
         task_color.style.borderWidth = '2px';
         document.getElementById('err_msg_task_character_limit').style.display = 'block';
         result = false;
    }

    //      期限が開始日より後だった場合の処理
    if (!(!start || !deadline) && start_date > deadline_date) {
        start_color.style.borderColor = deadline_color.style.borderColor = 'red';
        start_color.style.borderWidth = deadline_color.style.borderWidth = '2px';
        document.getElementById('err_msg_starts_after_deadline').style.display = 'block';
        result = false;
    }
    //      説明文が1001文字以上だった時の処理
    if (description.length >= 1001) {
        description_color.style.borderColor = 'red';
        description_color.style.borderWidth = '2px';
        document.getElementById('err_msg_description_character_limit').style.display= 'block';
        result = false;
    }

    return result;
}

function update_validate() {
    task = document.getElementById('update_task').value;
    start = document.getElementById('update_start').value;
    deadline = document.getElementById('update_deadline').value;
    description = document.getElementById('update_description').value;
    start_date = Date.parse(start);
    deadline_date = Date.parse(deadline);
    task_color = document.getElementById('update_task');
    start_color = document.getElementById('update_start');
    deadline_color = document.getElementById('update_deadline');
    description_color = document.getElementById('update_description');
    result = true;

    document.getElementById('err_msg_update_task_blank').style.display = '';
    document.getElementById('err_msg_update_task_character_limit').style.display = '';
    document.getElementById('err_msg_update_starts_after_deadline').style.display = '';
    document.getElementById('err_msg_update_description_character_limit').style.display = '';
    task_color.style.borderColor = task_color.style.borderWidth = '';
    start_color.style.borderColor = start_color.style.borderWidth = '';
    deadline_color.style.borderColor = deadline_color.style.borderWidth = '';
    description_color.style.borderColor = description_color.style.borderWidth = '';

    if (!task || task.trim().length == 0) {
        task_color.style.borderColor = 'red';
        task_color.style.borderWidth = '2px';
        document.getElementById('err_msg_update_task_blank').style.display = 'block';
        result = false;
    }

    if (task.length >= 31) {
        task_color.style.borderColor = 'red';
        task_color.style.borderWidth = '2px';
        document.getElementById('err_msg_update_task_character_limit').style.display = 'block';
        result = false;
    }

    if (!(!start || !deadline) && start_date > deadline_date) {
        start_color.style.borderColor = deadline_color.style.borderColor = 'red';
        start_color.style.borderWidth = deadline_color.style.borderWidth = '2px';
        document.getElementById('err_msg_update_starts_after_deadline').style.display = 'block';
        result = false;
    }

    if (description.length >= 1001) {
        description_color.style.borderColor = 'red';
        description_color.style.borderWidth = '2px';
        document.getElementById('err_msg_update_description_character_limit').style.display = 'block';
        result = false;
    }

    return result;
}