import SearchBar from '@/components/common/SearchBar/SearchBar';
import * as S from '@/components/layout/Header/Header.styles';
import { getUserIsLogin } from '@/store/userState';
import { useRecoilValue } from 'recoil';

const Header = () => {
	const isLogin = useRecoilValue(getUserIsLogin);

	return (
		<S.Container>
			<S.HeaderSection>
				<S.StyledLink to="/">
					<S.LogoLink>공식</S.LogoLink>
				</S.StyledLink>
				<S.SearchBarBox>
					<SearchBar />
				</S.SearchBarBox>
			</S.HeaderSection>
			<S.NavBar>
				{isLogin ? (
					<>
						<S.NavBarItem to="">마이페이지</S.NavBarItem>
						<S.LogOutItem>로그아웃</S.LogOutItem>
					</>
				) : (
					<S.NavBarItem to="/login">로그인</S.NavBarItem>
				)}

				<S.NavBarItem to="/category">글 쓰러 가기</S.NavBarItem>
				<S.NavBarItem to="/articles/question">에러 카테고리</S.NavBarItem>
				<S.NavBarItem to="/articles/discussion">토론 카테고리</S.NavBarItem>
				<S.NavBarItem to="/">문의하기</S.NavBarItem>
			</S.NavBar>
		</S.Container>
	);
};

export default Header;
