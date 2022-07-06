import { Link } from 'react-router-dom';

const Home = () => (
	<div>
		<Link to="/login">로그인</Link>
		<Link to="/category">카테고리 선택하기</Link>
	</div>
);

export default Home;
