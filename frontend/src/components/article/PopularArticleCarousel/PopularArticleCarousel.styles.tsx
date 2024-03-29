import { AiOutlineLeft } from 'react-icons/ai';

import { TwoLineTextOverFlow } from '@/styles/mixin';
import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const animationTiming = {
	duration: 500,
	iterations: 1,
};

export const Container = styled.section`
	display: flex;

	align-items: center;
	width: 85%;

	margin: 0 auto;
	overflow: hidden;
	scroll-snap-type: x mandatory;

	${({ theme }) => css`
		height: ${theme.size.SIZE_280};
		z-index: ${theme.zIndex.POPULAR_ARTICLES};

		@media (min-width: ${theme.breakpoints.DESKTOP_SMALL}) {
			width: 75%;
			height: ${theme.size.SIZE_280};
		}
	`}
`;

export const LeftBackgroundArticle = styled.div`
	position: absolute;

	top: 0;
	left: 0;

	width: 50%;
	height: 100%;

	${({ theme }) => css`
		border-radius: ${theme.size.SIZE_010};

		z-index: ${theme.zIndex.ARTICLE_BACKGROUND_CONTENT};
	`}
`;

export const RightBackgroundArticle = styled.div`
	position: absolute;

	top: 0;
	right: 0;

	width: 50%;
	height: 100%;

	${({ theme }) => css`
		border-radius: ${theme.size.SIZE_010};

		z-index: ${theme.zIndex.ARTICLE_BACKGROUND_CONTENT};
	`}
`;

export const ArrowButton = styled.button`
	width: ${({ theme }) => theme.size.SIZE_032};
	height: ${({ theme }) => theme.size.SIZE_032};
	display: flex;
	position: absolute;

	justify-content: center;
	align-items: center;

	opacity: 0.5;
	padding: 0;

	&:hover,
	&:active {
		opacity: 1;
	}

	${({ theme }) => css`
		font-size: ${theme.size.SIZE_020};

		color: ${theme.colors.WHITE};
		z-index: ${theme.zIndex.ARTICLE_ARROW_BUTTON};
	`}

	border-color: transparent;
	border-radius: ${({ theme }) => theme.size.SIZE_006};
	background-color: ${({ theme }) => theme.colors.BLACK_400};
	background-repeat: no-repeat;
	cursor: pointer;
`;

export const LeftArrowButton = styled(ArrowButton)`
	${({ theme }) => css`
		left: ${theme.size.SIZE_004};
	`}

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_SMALL}) {
		left: ${({ theme }) => theme.size.SIZE_050};
	}
`;

export const RightArrowButton = styled(ArrowButton)`
	${({ theme }) => css`
		right: ${theme.size.SIZE_004};
	`}

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_SMALL}) {
		right: ${({ theme }) => theme.size.SIZE_050};
	}
`;

export const LeftArrowIcon = styled(AiOutlineLeft)``;

export const RightArrowIcon = styled(AiOutlineLeft)`
	transform: rotate(180deg);
`;

export const ArticleContent = styled.div`
	display: flex;

	justify-content: space-between;
	width: 100%;

	${({ theme }) => css`
		gap: ${theme.size.SIZE_016};
		padding: ${theme.size.SIZE_016};
		border-radius: ${theme.size.SIZE_010};

		padding: ${theme.size.SIZE_010};
		z-index: ${theme.zIndex.ARTICLE_POPULAR_CONTENT};
	`}
`;

export const Title = styled.h2`
	width: 100%;
	${TwoLineTextOverFlow}

	${({ theme }) => css`
		font-size: ${theme.size.SIZE_014};
		margin-top: ${theme.size.SIZE_004};
	`}
`;

export const ArticleInfo = styled.div`
	display: flex;

	justify-content: space-between;
`;

export const ProfileBox = styled.div`
	display: flex;

	align-items: center;
	gap: ${({ theme }) => theme.size.SIZE_006};
`;

export const UserImg = styled.img`
	width: ${({ theme }) => theme.size.SIZE_024};
	height: ${({ theme }) => theme.size.SIZE_024};

	border-radius: 50%;

	object-fit: cover;
	object-position: center;
`;

export const UserName = styled.span`
	font-size: ${({ theme }) => theme.size.SIZE_012};

	color: ${({ theme }) => theme.colors.BLACK_600};
`;

export const CommentBox = styled.div`
	display: flex;

	align-items: center;
	gap: ${({ theme }) => theme.size.SIZE_006};
`;

export const CommentCount = styled.span`
	font-size: ${({ theme }) => theme.size.SIZE_012};

	color: ${({ theme }) => theme.colors.BLACK_600};
`;
