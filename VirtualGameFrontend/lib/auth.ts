// Gestión de autenticación JWT
export interface User {
  id: number
  nombre: string
  email: string
  photoUrl?: string
  isAdmin?: boolean
}

export interface AuthResponse {
  jwt: string
  user: User
}

class AuthManager {
  private tokenKey = "ciudad_nueva_token"
  private userKey = "ciudad_nueva_user"

  // Guardar token y usuario
  setAuth(authResponse: AuthResponse) {
    localStorage.setItem(this.tokenKey, authResponse.jwt)
    localStorage.setItem(this.userKey, JSON.stringify(authResponse.user))
  }

  // Obtener token
  getToken(): string | null {
    return localStorage.getItem(this.tokenKey)
  }

  // Obtener usuario actual
  getCurrentUser(): User | null {
    const userStr = localStorage.getItem(this.userKey)
    return userStr ? JSON.parse(userStr) : null
  }

  // Verificar si está autenticado
  isAuthenticated(): boolean {
    return !!this.getToken()
  }

  // Verificar si es administrador
  isAdmin(): boolean {
    const user = this.getCurrentUser()
    return user?.isAdmin || false
  }

  // Cerrar sesión
  logout() {
    localStorage.removeItem(this.tokenKey)
    localStorage.removeItem(this.userKey)
  }

  // Headers para peticiones autenticadas
  getAuthHeaders() {
    const token = this.getToken()
    return token ? { Authorization: `Bearer ${token}` } : {}
  }
}

export const authManager = new AuthManager()
