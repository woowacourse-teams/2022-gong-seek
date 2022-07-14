import styled from '@emotion/styled';
import { AiOutlineHeart } from 'react-icons/ai';
import { AiFillHeart } from 'react-icons/ai';

export const Container = styled.div`
	display: flex;
	flex-direction: column;
	gap: ${({ theme }) => theme.size.SIZE_010};
	justify-content: center;
	align-items: center;
`;

export const Header = styled.div`
	width: 90%;
	display: flex;
	justify-content: space-between;
	align-items: center;
`;

export const CategoryTitle = styled.h2`
	font-size: ${({ theme }) => theme.size.SIZE_018};
`;

export const UserProfile = styled.div`
	display: flex;
	justify-content: center;
	align-items: center;
`;

export const UserProfileImg = styled.img`
	width: ${({ theme }) => theme.size.SIZE_050};
	height: ${({ theme }) => theme.size.SIZE_050};
	object-fit: cover;
	object-position: center;
	border-radius: 50%;
`;

export const UserName = styled.span``;

export const ArticleInfo = styled.div`
	display: flex;
	flex-direction: column;
	gap: ${({ theme }) => theme.size.SIZE_010};
`;

export const ArticleTitle = styled.h3`
	font-size: ${({ theme }) => theme.size.SIZE_014};
	color: ${({ theme }) => theme.colors.BLACK_600};
	line-height: normal;
	word-break: keep-all;
`;

export const ArticleDetailInfo = styled.div`
	display: flex;
	gap: ${({ theme }) => theme.size.SIZE_010};
	justify-content: space-between;
`;

export const DetailBox = styled.span`
	font-weight: 300;
	color: ${({ theme }) => theme.colors.BLACK_600};
	opacity: 0.5;
	font-size: ${({ theme }) => theme.size.SIZE_012};
`;

export const Footer = styled.div`
	width: 100%;
	display: flex;
	flex-direction: row;
	position: relative;
	justify-content: space-between;
`;

export const WritingOrderBox = styled.div``;

export const ButtonWrapper = styled.div`
	display: flex;
	gap: ${({ theme }) => theme.size.SIZE_004};
`;

export const Button = styled.div`
	border: none;
	background-color: transparent;
	font-size: ${({ theme }) => theme.size.SIZE_012};
	color: ${({ theme }) => theme.colors.BLACK_600};
	opacity: 0.5;
	text-decoration: none;

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
