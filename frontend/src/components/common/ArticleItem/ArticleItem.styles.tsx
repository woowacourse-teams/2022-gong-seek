import { AiOutlineHeart } from 'react-icons/ai';
import { AiFillHeart } from 'react-icons/ai';

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

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP}) {
		height: ${({ theme }) => theme.size.SIZE_300};
		width: ${({ theme }) => theme.size.SIZE_260};
	}
`;

export const ArticleItemTitle = styled.h2`
	display: flex;

	gap: ${({ theme }) => theme.size.SIZE_010};

	height: ${({ theme }) => theme.size.SIZE_040};

	font-size: ${({ theme }) => theme.size.SIZE_016};
	font-weight: 600;

	overflow: hidden;
	text-overflow: ellipsis;

	white-space: normal;
	line-height: 1.2;
	word-wrap: break-word;
	&:hover,
	&:active {
		text-decoration: underline;
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
	display: flex;
	justify-content: space-between;

	margin-top: ${({ theme }) => theme.size.SIZE_008};
`;

export const Content = styled.div`
	height: ${({ theme }) => theme.size.SIZE_032};

	font-size: ${({ theme }) => theme.size.SIZE_012};
	line-height: ${({ theme }) => theme.size.SIZE_016};
	text-overflow: ellipsis;
	overflow: hidden;

	margin-top: ${({ theme }) => theme.size.SIZE_016};

	&:hover,
	&:active {
		text-decoration: underline;
	}
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
	font-size: ${({ theme }) => theme.size.SIZE_012};
`;

export const RightFooterBox = styled.div`
	display: flex;

	align-items: center;
	gap: ${({ theme }) => theme.size.SIZE_012};

	margin-left: auto;
`;

export const HashTagListBox = styled.div`
	display: flex;
	gap: ${({ theme }) => theme.size.SIZE_004};

	margin-top: ${({ theme }) => theme.size.SIZE_010};
`;

export const HashTagItem = styled.div`
	font-size: ${({ theme }) => theme.size.SIZE_010};
	border: ${({ theme }) => theme.size.SIZE_001} solid ${({ theme }) => theme.colors.GRAY_500};
	border-radius: ${({ theme }) => theme.size.SIZE_004};

	padding: ${({ theme }) => theme.size.SIZE_004};
`;
