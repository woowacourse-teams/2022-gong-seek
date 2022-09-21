import { AiOutlineHeart, AiFillHeart } from 'react-icons/ai';

import { keyframes } from '@emotion/react';
import styled from '@emotion/styled';

const scaleAnimation = keyframes`
	0%{

	}

	100% {
		transform: scale(1.01);
	}
`;

export const Container = styled.div`
	display: flex;

	flex-direction: column;

	width: 80%;
	height: ${({ theme }) => theme.size.SIZE_160};

	border-radius: ${({ theme }) => theme.size.SIZE_010};

	box-shadow: 0 8px 24px ${({ theme }) => theme.boxShadows.secondary};

	padding: ${({ theme }) => theme.size.SIZE_016};

	&:hover,
	&:active {
		animation: ${scaleAnimation} 0.3s ease-in;
		animation-fill-mode: forwards;

		cursor: pointer;
	}

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_LARGE}) {
		height: ${({ theme }) => theme.size.SIZE_220};
		width: ${({ theme }) => theme.size.SIZE_300};
	}
`;

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
		text-decoration: underline;
	}

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_LARGE}) {
		line-height: 1.5;
	}
`;

export const Views = styled.div`
	font-size: ${({ theme }) => theme.size.SIZE_010};
	font-weight: 400;
`;

export const CommentCount = styled.div`
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
`;

export const FooterBox = styled.div`
	display: flex;

	align-items: center;

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
	gap: ${({ theme }) => theme.size.SIZE_004};

	flex-wrap: nowrap;
	overflow: hidden;

	margin: ${({ theme }) => theme.size.SIZE_020} 0;

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_LARGE}) {
		flex-wrap: wrap;
	}
`;

export const HashTagItem = styled.div`
	height: fit-content;

	max-width: ${({ theme }) => theme.size.SIZE_110};
	font-size: ${({ theme }) => theme.size.SIZE_014};
	border: ${({ theme }) => theme.size.SIZE_001} solid ${({ theme }) => theme.colors.GRAY_500};
	border-radius: ${({ theme }) => theme.size.SIZE_004};

	text-overflow: ellipsis;
	white-space: nowrap;
	overflow: hidden;
	padding: ${({ theme }) => theme.size.SIZE_004};
`;
