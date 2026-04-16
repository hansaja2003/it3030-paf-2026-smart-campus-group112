import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import heroImg from './assets/hero.png'
import './App.css'

function App() {
  return (
    <div className="p-8 text-center">
      <h1 className="text-5xl font-bold text-blue-600 underline">
        Hello Tailwind!
      </h1>
      <p className="text-green-500 text-xl mt-4">
        If this text is blue and green, Tailwind works! 🎉
      </p>
      <button className="mt-6 bg-red-500 text-white px-4 py-2 rounded-lg hover:bg-red-700">
        This is a styled button
      </button>
    </div>
  )
}

export default App