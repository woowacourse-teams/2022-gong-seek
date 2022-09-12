import { Link } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import gongseek from '@/assets/gongseek.png';
import SearchBar from '@/components/common/SearchBar/SearchBar';
import UserProfileIcon from '@/components/common/UserProfileIcon/UserProfileIcon';
import * as S from '@/components/layout/Header/Header.styles';
import { searchState } from '@/store/searchState';
import { getUserIsLogin } from '@/store/userState';
import { theme } from '@/styles/Theme';

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
				<S.NavBarItem to="/category">글 쓰러 가기</S.NavBarItem>
				<S.NavBarItem to="/articles/question">질문 카테고리</S.NavBarItem>
				<S.NavBarItem to="/articles/discussion">토론 카테고리</S.NavBarItem>
				<S.NavBarItem to="/hash-tag">해시태그로 검색하기</S.NavBarItem>
				<S.NavBarItem to="/inquire">문의하기</S.NavBarItem>
				{isLogin ? (
					<UserProfileIcon />
				) : (
					<S.NavBarItem to="/login" css={{ marginLeft: 'auto', marginRight: theme.size.SIZE_160 }}>
						로그인
					</S.NavBarItem>
				)}
			</S.NavBar>
		</S.Container>
	);
};

export default Header;
