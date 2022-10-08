import useModal from '../../../hooks/common/useModal';
import { useNavigate } from 'react-router-dom';
import { useRecoilValue } from 'recoil';

import * as S from '@/components/@common/MenuSlider/MenuSlider.styles';
import { ACCESSTOKEN_KEY } from '@/constants';
import { URL } from '@/constants/url';
import useDeleteRefreshToken from '@/hooks/login/useDeleteRefreshToken';
import { getUserIsLogin } from '@/store/userState';

const MenuSlider = () => {
	const isLogin = useRecoilValue(getUserIsLogin);
	const { mutate: mutateDeleteRefreshToken } = useDeleteRefreshToken();
	const { hideModal } = useModal();
	const navigate = useNavigate();

	const onLogoutClick = () => {
		if (confirm('정말로 로그아웃 하시겠습니까?')) {
			mutateDeleteRefreshToken();
			localStorage.removeItem(ACCESSTOKEN_KEY);
			navigate(URL.HOME);
		}
	};

	return (
		<S.MenuBox>
			<S.Header>
				<S.BackButtonBox onClick={hideModal}>
					<S.BackButton />
				</S.BackButtonBox>
			</S.Header>
			<S.LinkBox>
				{isLogin && (
					<S.LinkItem
						onClick={() => {
							onLogoutClick();
							hideModal();
						}}
					>
						로그아웃
					</S.LinkItem>
				)}
				{!isLogin && (
					<S.LinkItem
						onClick={() => {
							navigate(URL.LOGIN);
							hideModal();
						}}
					>
						로그인
					</S.LinkItem>
				)}
				<S.LinkItem
					onClick={() => {
						navigate(URL.CATEGORY_SELECTOR);
						hideModal();
					}}
				>
					글 쓰러 가기
				</S.LinkItem>
				<S.LinkItem
					onClick={() => {
						navigate(URL.CATEGORY_QUESTION);
						hideModal();
					}}
				>
					질문 카테고리
				</S.LinkItem>
				<S.LinkItem
					onClick={() => {
						navigate(URL.CATEGORY_DISCUSSION);
						hideModal();
					}}
				>
					토론 카테고리
				</S.LinkItem>
				<S.LinkItem
					onClick={() => {
						navigate(URL.HASH_TAG_SEARCH);
						hideModal();
					}}
				>
					해시태그로 검색하기
				</S.LinkItem>
				<S.LinkItem
					onClick={() => {
						navigate(URL.INQUIRE);
						hideModal();
					}}
				>
					문의하기
				</S.LinkItem>
			</S.LinkBox>
		</S.MenuBox>
	);
};

export default MenuSlider;
