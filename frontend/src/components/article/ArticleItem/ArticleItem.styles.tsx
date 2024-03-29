import { AiOutlineHeart, AiFillHeart } from 'react-icons/ai';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const ArticleItemTitle = styled.h2`
	display: flex;
	width: 100%;
	height: ${({ theme }) => theme.size.SIZE_080};

	font-size: ${({ theme }) => theme.size.SIZE_016};
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: pre-wrap;
	line-height: 2;
	word-break: break-all;
	&:hover,
	&:active {
		color: ${({ theme }) => theme.colors.BLACK_600};
	}

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_LARGE}) {
		line-height: 1.5;
	}
`;

export const Views = styled.div`
	display: flex;
	justify-content: center;
	align-items: center;
	gap: ${({ theme }) => theme.size.SIZE_006};
	font-size: ${({ theme }) => theme.size.SIZE_010};
	font-weight: 400;
`;

export const CommentCount = styled.div`
	display: flex;
	justify-content: center;
	align-items: center;
	gap: ${({ theme }) => theme.size.SIZE_006};
	font-size: ${({ theme }) => theme.size.SIZE_010};
	font-weight: 400;
`;

export const ArticleInfoBox = styled.div`
	width: 100%;
	display: flex;
	justify-content: space-between;

	margin-top: ${({ theme }) => theme.size.SIZE_008};
`;

export const ArticleInfoSubBox = styled.div`
	display: flex;
	align-items: center;
	gap: ${({ theme }) => theme.size.SIZE_010};
`;

export const UserProfile = styled.img`
	width: ${({ theme }) => theme.size.SIZE_032};
	height: ${({ theme }) => theme.size.SIZE_032};

	border-radius: 50%;

	object-fit: cover;
	object-position: center;
`;

export const ProfileBox = styled.div`
	display: flex;
	align-items: center;
	gap: ${({ theme }) => theme.size.SIZE_012};
	max-width: 80%;
`;

export const UserName = styled.span`
	overflow: hidden;
	width: 80%;
	text-overflow: ellipsis;
	white-space: nowrap;
`;

export const FooterBox = styled.div`
	display: flex;
	justify-content: space-between;

	margin-top: auto;
`;

export const EmptyHeart = styled(AiOutlineHeart)`
	font-size: ${({ theme }) => theme.size.SIZE_024};
`;

export const FillHeart = styled(AiFillHeart)`
	font-size: ${({ theme }) => theme.size.SIZE_024};

	color: ${({ theme }) => theme.colors.RED_600};
`;

export const HeartBox = styled.div`
	display: flex;

	align-items: center;
	gap: ${({ theme }) => theme.size.SIZE_010};
`;

export const HeartButton = styled.button`
	border: none;
	background-color: transparent;
`;

export const ArticleTimeStamp = styled.div`
	font-size: ${({ theme }) => theme.size.SIZE_010};
	font-weight: 400;
`;

export const RightFooterBox = styled.div`
	display: flex;

	align-items: center;
	gap: ${({ theme }) => theme.size.SIZE_012};

	margin-left: auto;
`;

export const HashTagListBox = styled.div`
	display: flex;
	height: 70%;

	flex-wrap: nowrap;
	overflow: hidden;

	${({ theme }) => css`
		gap: ${theme.size.SIZE_004};
		margin: ${theme.size.SIZE_020} 0;

		@media (min-width: ${theme.breakpoints.DESKTOP_LARGE}) {
			flex-wrap: wrap;
		}
	`}
`;

export const HashTagItem = styled.div`
	height: fit-content;
	text-overflow: ellipsis;
	white-space: nowrap;
	overflow: hidden;

	${({ theme }) => css`
		max-width: ${theme.size.SIZE_110};
		font-size: ${theme.size.SIZE_014};
		border: ${theme.size.SIZE_001} solid ${theme.colors.GRAY_500};
		border-radius: ${theme.size.SIZE_004};
		padding: ${theme.size.SIZE_004};
	`}
`;
