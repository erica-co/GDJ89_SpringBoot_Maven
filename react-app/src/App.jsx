import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import { BrowserRouter } from 'react-router-dom'
import Header from './layout/header'
import AppRoutes from './layout/AppRoutes'
import Footer from './layout/Footer'


function App() {
  const [count, setCount] = useState(0)


  return (
    <>
      <BrowserRouter>
      <Header></Header>
      <AppRoutes></AppRoutes>
      
      
      <Footer></Footer>
      </BrowserRouter>
    </>
  )
}

export default App
