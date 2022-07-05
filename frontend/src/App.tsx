import styled from '@emotion/styled';
import { Routes, Route } from 'react-router-dom';
import Header from '@/components/layout/Header/Header';
import TabBar from '@/components/layout/TabBar/TabBar';
import Login from '@/pages/Login';
import WritingArticles from '@/pages/WritingArticles';

const Layout = styled.div`
	position: relative;
	height: 100vh;
	width: 100vw;
`;

const Content = styled.main`
	width: 100%;
	padding-bottom: 7rem;
`;

const App = () => (
	<Layout>
		<Header />
		<Content>
			<Routes>
				<Route path="/article" element={<WritingArticles />} />
				<Route path="/" element={<Login />} />
			</Routes>
		</Content>
		<TabBar />
	</Layout>
);

export default App;
