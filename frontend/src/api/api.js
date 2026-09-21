const API_BASE_URL = 'http://localhost:8081'

export const getCredentials = () => {
  const username = localStorage.getItem('username')
  const password = localStorage.getItem('password')

  if (!username || !password) {
    throw new Error('Login information is missing. Please login again.')
  }

  return btoa(`${username}:${password}`)
}

export const apiRequest = async (endpoint, options = {}) => {
  const credentials = getCredentials()

  const response = await fetch(`${API_BASE_URL}${endpoint}`, {
    ...options,
    headers: {
      ...(options.headers || {}),
      Authorization: `Basic ${credentials}`,
    },
  })

  const contentType = response.headers.get('content-type') || ''

  const data = contentType.includes('application/json')
    ? await response.json()
    : await response.text()

  if (!response.ok) {
    const message =
      typeof data === 'string'
        ? data
        : data?.message || 'Something went wrong'

    throw new Error(message)
  }

  return data
}

export default API_BASE_URL