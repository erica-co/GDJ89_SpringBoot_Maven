import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import { BrowserRouter } from 'react-router-dom'
import Header from './layout/header'
import AppRoutes from './layout/AppRoutes'
import Footer from './layout/Footer'
import { Base_URL } from './contexts/Urlcontext'
import { LoginStateProvider } from './contexts/LoginStateContext'


function App() {
  const [count, setCount] = useState(0)


  return (
    <>
      <BrowserRouter>
      <Base_URL.Provider value="http://localhost:81">
        <LoginStateProvider>

          <Header></Header>
          <AppRoutes></AppRoutes>
          <Footer></Footer>
          
        </LoginStateProvider>
      </Base_URL.Provider>
      
      
      </BrowserRouter>
    </>
  )
}

export default App
