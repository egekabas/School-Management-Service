import logo from './logo.svg';
import './App.css';
import Login from "./pages/Login";
import Home from "./pages/Home";
import {BrowserRouter, Routes, Route,} from "react-router-dom";
import Logout from "./pages/Logout";


function App() {
    return (
        <div className="App">
            <BrowserRouter>
                <Routes>
                    <Route path='/login' element={<Login/>}/>
                    <Route path='/logout' element={<Logout/>}/>
                    <Route path='/' element={<Home/>}/>
                </Routes>
            </BrowserRouter>
        </div>
    );
}

export default App;
