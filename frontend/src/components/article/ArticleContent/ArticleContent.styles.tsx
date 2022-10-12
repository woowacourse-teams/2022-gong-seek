import { AiOutlineHeart } from 'react-icons/ai';
import { AiFillHeart } from 'react-icons/ai';

import { css } from '@emotion/react';
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
	font-weight: 800;
	${({ theme, category }) => css`
		font-size: ${theme.size.SIZE_020};
		color: ${category === '토론' ? `${theme.colors.BLUE_500}` : `${theme.colors.RED_500}`};
	`}
`;

export const UserProfile = styled.div`
	display: flex;
	justify-content: center;
	align-items: center;
`;

export const UserProfileImg = styled.img`
	border-radius: 50%;

	object-fit: cover;
	object-position: center;

	${({ theme }) => css`
		width: ${theme.size.SIZE_032};
		height: ${theme.size.SIZE_032};

		margin-right: ${theme.size.SIZE_014};
	`}
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
	word-break: break-all;

	${({ theme }) => css`
		font-size: ${theme.size.SIZE_014};
		line-height: ${theme.size.SIZE_026};

		color: ${theme.colors.BLACK_600};
	`}
`;

export const ArticleDetailInfo = styled.div`
	display: flex;
	justify-content: space-between;
	gap: ${({ theme }) => theme.size.SIZE_010};
`;

export const DetailBox = styled.span`
	font-weight: 300;
	opacity: 0.5;

	${({ theme }) => css`
		font-size: ${theme.size.SIZE_012};
		color: ${theme.colors.BLACK_600};
	`}
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
	text-decoration: none;
	opacity: 0.5;

	background-color: transparent;

	${({ theme }) => css`
		font-size: ${theme.size.SIZE_012};
		color: ${theme.colors.BLACK_600};

		&:hover,
		&:active {
			color: ${theme.colors.PURPLE_500};
			opacity: 1;
		}
	`}
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
	width: 100%;

	display: flex;
	flex-wrap: wrap;

	gap: ${({ theme }) => theme.size.SIZE_004};
`;

export const HashTagItem = styled.div`
	background: transparent;

	${({ theme }) => css`
		font-size: ${theme.size.SIZE_014};
		border: ${theme.size.SIZE_001} solid ${theme.colors.GRAY_500};
		border-radius: ${theme.size.SIZE_004};

		padding: ${theme.size.SIZE_004};
	`}
`;
