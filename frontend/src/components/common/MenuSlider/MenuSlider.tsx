import reactDom from 'react-dom';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import * as S from '@/components/common/MenuSlider/MenuSlider.styles';
import { ACCESSTOKEN_KEY } from '@/constants';
import { URL } from '@/constants/url';
import { getUserIsLogin } from '@/store/userState';

export interface MenuSliderProps {
	closeSlider: () => void;
}

const MenuSlider = ({ closeSlider }: MenuSliderProps) => {
	const menuSlider = document.getElementById('menu-slider');
	const isLogin = useRecoilValue(getUserIsLogin);

	const navigate = useNavigate();

	const onLogoutClick = () => {
		if (confirm('정말로 로그아웃 하시겠습니까?')) {
			localStorage.removeItem(ACCESSTOKEN_KEY);
			window.location.href = URL.HOME;
		}
	};

	if (menuSlider === null) {
		throw new Error('슬라이더를 찾지 못하였습니다');
	}

	return reactDom.createPortal(
		<S.MenuBox>
			<S.Header>
				<S.BackButtonBox onClick={closeSlider}>
					<S.BackButton />
				</S.BackButtonBox>
			</S.Header>
			<S.LinkBox>
				{isLogin && (
					<S.LinkItem
						onClick={() => {
							onLogoutClick();
							closeSlider();
						}}
					>
						로그아웃
					</S.LinkItem>
				)}
				{!isLogin && (
					<S.LinkItem
						onClick={() => {
							navigate(URL.LOGIN);
							closeSlider();
						}}
					>
						로그인
					</S.LinkItem>
				)}
				<S.LinkItem
					onClick={() => {
						navigate(URL.CATEGORY_SELECTOR);
						closeSlider();
					}}
				>
					글 쓰러 가기
				</S.LinkItem>
				<S.LinkItem
					onClick={() => {
						navigate(URL.CATEGORY_QUESTION);
						closeSlider();
					}}
				>
					질문 카테고리
				</S.LinkItem>
				<S.LinkItem
					onClick={() => {
						navigate(URL.CATEGORY_DISCUSSION);
						closeSlider();
					}}
				>
					토론 카테고리
				</S.LinkItem>
				<S.LinkItem
					onClick={() => {
						navigate(URL.HASH_TAG_SEARCH);
						closeSlider();
					}}
				>
					해시태그로 검색하기
				</S.LinkItem>
				<S.LinkItem
					onClick={() => {
						navigate(URL.INQUIRE);
						closeSlider();
					}}
				>
					문의하기
				</S.LinkItem>
			</S.LinkBox>
		</S.MenuBox>,
		menuSlider,
	);
};

export default MenuSlider;
