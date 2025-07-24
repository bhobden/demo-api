export async function login(username, password) {
  const response = await fetch("/v1/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username, password }),
  });
  return response.json();
}

export async function createUser(userData) {
  const response = await fetch("/v1/users", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(userData),
  });
  return response.json();
}

export async function getUser(userId, jwt) {
  const response = await fetch(`/v1/users/${userId}`, {
    headers: { Authorization: `Bearer ${jwt}` }
  });
  return response.json();
}

export async function updateUser(userId, userData, jwt) {
  const response = await fetch(`/v1/users/${userId}`, {
    method: "PATCH",
    headers: { "Content-Type": "application/json", Authorization: `Bearer ${jwt}` },
    body: JSON.stringify(userData),
  });
  return response.json();
}

export async function deleteUser(userId, jwt) {
  const response = await fetch(`/v1/users/${userId}`, {
    method: "DELETE",
    headers: { Authorization: `Bearer ${jwt}` }
  });
  return response.status === 204 ? null : response.json();
}

export async function getAccounts(jwt) {
  const response = await fetch(`/v1/accounts`, {
    headers: { Authorization: `Bearer ${jwt}` }
  });
  return response.json();
}

export async function getAccount(accountId, jwt) {
  const response = await fetch(`/v1/accounts/${accountId}`, {
    headers: { Authorization: `Bearer ${jwt}` }
  });
  return response.json();
}

export async function createAccount(accountData, jwt) {
  const response = await fetch("/v1/accounts", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${jwt}`
    },
    body: JSON.stringify(accountData),
  });
  return response.json();
}

export async function updateAccount(accountId, accountData, jwt) {
  const response = await fetch(`/v1/accounts/${accountId}`, {
    method: "PATCH",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${jwt}`
    },
    body: JSON.stringify(accountData),
  });
  return response.json();
}

export async function deleteAccount(accountId, jwt) {
  const response = await fetch(`/v1/accounts/${accountId}`, {
    method: "DELETE",
    headers: { Authorization: `Bearer ${jwt}` }
  });
  return response.status === 204 ? null : response.json();
}

export async function createTransaction(accountNumber, data, jwt) {
  const res = await fetch(`/v1/accounts/${accountNumber}/transactions`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${jwt}`
    },
    body: JSON.stringify(data)
  });
  return res.json();
}

export async function getTransactionsForAccount(accountNumber, jwt) {
  const res = await fetch(`/v1/accounts/${accountNumber}/transactions`, {
    headers: { Authorization: `Bearer ${jwt}` }
  });
  return res.json();
}

export async function getTransaction(accountNumber, transactionId, jwt) {
  const response = await fetch(`/v1/accounts/${accountNumber}/transactions/${transactionId}`, {
    headers: { Authorization: `Bearer ${jwt}` }
  });
  return response.json();
}
