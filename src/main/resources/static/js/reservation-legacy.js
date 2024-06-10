let isEditing = false;
const RESERVATION_API_ENDPOINT = '/reservations';

document.addEventListener('DOMContentLoaded', () => {
  document.getElementById('add-button').addEventListener('click', addInputRow);

  requestRead(RESERVATION_API_ENDPOINT)
      .then(render)
      .catch(error => console.error('Error fetching reservations:', error));
});

function render(data) {
  const tableBody = document.getElementById('table-body');
  tableBody.innerHTML = '';

  data.forEach(item => {
    const row = tableBody.insertRow();

    row.insertCell(0).textContent = item.id;
    row.insertCell(1).textContent = item.name;
    row.insertCell(2).textContent = item.date;
    row.insertCell(3).textContent = item.time;

    const actionCell = row.insertCell(row.cells.length);
    actionCell.appendChild(createActionButton('삭제', 'btn-danger', deleteRow));
  });
}

function createActionButton(label, className, eventListener) {
  const button = document.createElement('button');
  button.textContent = label;
  button.classList.add('btn', className, 'mr-2');
  button.addEventListener('click', eventListener);
  return button;
}

function addInputRow() {
  if (isEditing) return;  // 이미 편집 중인 경우 추가하지 않음

  const tableBody = document.getElementById('table-body');
  const row = tableBody.insertRow();
  isEditing = true;

  const nameInput = createInput('text');
  const dateInput = createInput('date');
  const timeInput = createInput('time');

  const cellFieldsToCreate = ['', nameInput, dateInput, timeInput];

  cellFieldsToCreate.forEach((field, index) => {
    const cell = row.insertCell(index);
    if (typeof field === 'string') {
      cell.textContent = field;
    } else {
      cell.appendChild(field);
    }
  });

  const actionCell = row.insertCell(row.cells.length);
  actionCell.appendChild(createActionButton('확인', 'btn-custom', saveRow));
  actionCell.appendChild(createActionButton('취소', 'btn-secondary', () => {
    row.remove();
    isEditing = false;
  }));
}

function createInput(type) {
  const input = document.createElement('input');
  input.type = type;
  input.className = 'form-control';
  return input;
}

function createActionButton(label, className, eventListener) {
  const button = document.createElement('button');
  button.textContent = label;
  button.classList.add('btn', className, 'mr-2');
  button.addEventListener('click', eventListener);
  return button;
}

function saveRow(event) {
  // 이벤트 전파를 막는다
  event.stopPropagation();

  const row = event.target.parentNode.parentNode;
  const nameInput = row.querySelector('input[type="text"]');
  const dateInput = row.querySelector('input[type="date"]');
  const timeInput = row.querySelector('input[type="time"]');

  const reservation = {
    name: nameInput.value,
    date: dateInput.value,
    time: timeInput.value
  };

  requestCreate(reservation)
      .then(() => {
        location.reload();
      })
      .catch(error => console.error('Error:', error));

  isEditing = false;  // isEditing 값을 false로 설정
}

function deleteRow(event) {
  const row = event.target.closest('tr');
  const reservationId = row.cells[0].textContent;

  requestDelete(reservationId)
      .then(() => row.remove())
      .catch(error => console.error('Error:', error));
}

function requestCreate(reservation) {
  const requestOptions = {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(reservation)
  };

  return fetch(RESERVATION_API_ENDPOINT, requestOptions)
      .then(response => {
        if (response.status === 201) return response.json();
        throw new Error('Create failed');
      });
}

function requestDelete(id) {
  const requestOptions = {
    method: 'DELETE',
  };

  return fetch(`${RESERVATION_API_ENDPOINT}/${id}`, requestOptions)
      .then(response => {
        if (response.status !== 204) throw new Error('Delete failed');
      });
}

function requestRead(endpoint) {
  return fetch(endpoint)
      .then(response => {
        if (response.status === 200) return response.json();
        throw new Error('Read failed');
      });
}
