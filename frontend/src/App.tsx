import styled from '@emotion/styled';
import { Routes, Route } from 'react-router-dom';
import Header from './components/layout/Header/Header';
import TabBar from './components/layout/TabBar/TabBar';
import Login from './pages/Login';

const Layout = styled.div`
	position: relative;
	height: 100vh;
`;

const App = () => (
	<Layout>
		<Header />
		<Routes>
			<Route path="/" element={<Login />} />
		</Routes>
		<TabBar />
	</Layout>
);

export default App;
