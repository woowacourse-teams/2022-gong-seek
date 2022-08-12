import { AiOutlineHeart } from 'react-icons/ai';
import { AiFillHeart } from 'react-icons/ai';

import styled from '@emotion/styled';

export const Container = styled.div`
	display: flex;

	flex-direction: column;
	justify-content: center;
	align-items: center;
	gap: ${({ theme }) => theme.size.SIZE_010};

	width: 100%;
`;

export const Header = styled.div`
	display: flex;
	justify-content: space-between;
	align-items: center;

	width: 90%;
`;

export const CategoryTitle = styled.h2<{ category: string }>`
	font-size: ${({ theme }) => theme.size.SIZE_020};
	font-weight: 800;

	color: ${({ theme, category }) =>
		category === '토론' ? theme.colors.BLUE_500 : theme.colors.RED_500};
`;

export const UserProfile = styled.div`
	display: flex;
	justify-content: center;
	align-items: center;
`;

export const UserProfileImg = styled.img`
	width: ${({ theme }) => theme.size.SIZE_032};
	height: ${({ theme }) => theme.size.SIZE_032};

	border-radius: 50%;

	margin-right: ${({ theme }) => theme.size.SIZE_014};

	object-fit: cover;
	object-position: center;
`;

export const UserName = styled.span``;

export const ArticleInfo = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: flex-start;

	width: 100%;
	gap: ${({ theme }) => theme.size.SIZE_010};
`;

export const ArticleTitle = styled.h3`
	font-size: ${({ theme }) => theme.size.SIZE_014};
	line-height: ${({ theme }) => theme.size.SIZE_026};

	color: ${({ theme }) => theme.colors.BLACK_600};

	word-break: break-all;
`;

export const ArticleDetailInfo = styled.div`
	display: flex;
	justify-content: space-between;
	gap: ${({ theme }) => theme.size.SIZE_010};
`;

export const DetailBox = styled.span`
	font-weight: 300;
	font-size: ${({ theme }) => theme.size.SIZE_012};

	color: ${({ theme }) => theme.colors.BLACK_600};
	opacity: 0.5;
`;

export const TextViewerBox = styled.div`
	width: 100%;

	margin: ${({ theme }) => theme.size.SIZE_012}, 0;
`;

export const Footer = styled.div`
	display: flex;
	position: relative;

	flex-direction: row;
	justify-content: space-between;

	width: 100%;
`;

export const WritingOrderBox = styled.div``;

export const ButtonWrapper = styled.div`
	display: flex;

	gap: ${({ theme }) => theme.size.SIZE_004};
`;

export const Button = styled.div`
	border: none;

	font-size: ${({ theme }) => theme.size.SIZE_012};
	text-decoration: none;

	background-color: transparent;
	color: ${({ theme }) => theme.colors.BLACK_600};
	opacity: 0.5;

	&:hover,
	&:active {
		color: ${({ theme }) => theme.colors.PURPLE_500};
		opacity: 1;
	}
`;

export const LikeContentBox = styled.div`
	display: flex;

	justify-content: center;
	align-items: center;
	gap: ${({ theme }) => theme.size.SIZE_004};
`;

export const EmptyHeart = styled(AiOutlineHeart)`
	font-size: ${({ theme }) => theme.size.SIZE_024};
`;

export const FillHeart = styled(AiFillHeart)`
	font-size: ${({ theme }) => theme.size.SIZE_024};

	color: ${({ theme }) => theme.colors.RED_600};
`;

export const HashTagListBox = styled.section`
	display: flex;
	flex-wrap: wrap;

	gap: ${({ theme }) => theme.size.SIZE_004};
`;

export const HashTagItem = styled.div`
	background: transparent;
	border: ${({ theme }) => theme.size.SIZE_001} solid ${({ theme }) => theme.colors.GRAY_100};
	border-radius: ${({ theme }) => theme.size.SIZE_004};

	padding: ${({ theme }) => theme.size.SIZE_004};
`;
