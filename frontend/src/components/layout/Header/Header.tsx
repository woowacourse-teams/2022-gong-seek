import { Link } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import gongseek from '@/assets/gongseek.png';
import SearchBar from '@/components/common/SearchBar/SearchBar';
import * as S from '@/components/layout/Header/Header.styles';
import { searchState } from '@/store/searchState';
import { getUserIsLogin } from '@/store/userState';

const Header = () => {
	const isLogin = useRecoilValue(getUserIsLogin);
	const { isSearchOpen } = useRecoilValue(searchState);
	if (isSearchOpen) {
		return (
			<S.Container>
				<S.HeaderSection>
					<S.StyledLink to="/">
						<S.LogoImage src={gongseek} />
					</S.StyledLink>
					<S.SearchOpenBox>
						<Link to="/search-result ">
							<SearchBar isValid={false} />
						</Link>
					</S.SearchOpenBox>
				</S.HeaderSection>
			</S.Container>
		);
	}
	return (
		<S.Container>
			<S.HeaderSection>
				<S.StyledLink to="/">
					<S.LogoLink>공식</S.LogoLink>
				</S.StyledLink>
				<S.SearchBarBox>
					<Link to="/search-result ">
						<SearchBar isValid={true} />
					</Link>
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
