import { Link } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import gongseek from '@/assets/gongseek.png';
import SearchBar from '@/components/common/SearchBar/SearchBar';
import UserProfileIcon from '@/components/common/UserProfileIcon/UserProfileIcon';
import * as S from '@/components/layout/Header/Header.styles';
import { URL } from '@/constants/url';
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
					<S.StyledLink to={URL.HOME}>
						<S.LogoImage src={gongseek} />
					</S.StyledLink>

					<S.SearchOpenBox>
						<Link to={URL.SEARCH_RESULT}>
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
				<S.StyledLink to={URL.HOME}>
					<S.LogoLink>공식</S.LogoLink>
				</S.StyledLink>
				{/*TODO: Container부터 여기까지 코드 동일함 */}

				<S.SearchBarBox>
					<Link to={URL.SEARCH_RESULT}>
						<SearchBar isValid={true} />
					</Link>
				</S.SearchBarBox>
			</S.HeaderSection>
			{/*TODO: NavBar는 하나의 컴포넌트로 분리하는것이 좋을것 같다 */}
			<S.NavBar>
				<S.NavBarItemBox>
					<S.NavBarItem to={URL.CATEGORY_SELECTOR}>글 쓰러 가기</S.NavBarItem>
					<S.NavBarItem to={URL.CATEGORY_QUESTION}>질문 게시판</S.NavBarItem>
					<S.NavBarItem to={URL.CATEGORY_DISCUSSION}>토론 게시판</S.NavBarItem>
					<S.NavBarItem to={URL.HASH_TAG_SEARCH}>해시태그</S.NavBarItem>
					<S.NavBarItem to={URL.INQUIRE}>문의하기</S.NavBarItem>
				</S.NavBarItemBox>
				{isLogin ? (
					<S.ProfileIconBox>
						<UserProfileIcon />
					</S.ProfileIconBox>
				) : (
					<S.LoginIn to={URL.LOGIN}>로그인</S.LoginIn>
				)}
			</S.NavBar>
		</S.Container>
	);
};

export default Header;
