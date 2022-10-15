import { AiOutlineMessage, AiOutlineLeft } from 'react-icons/ai';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const showPopularSlider = [
	{
		transform: 'rotateY(-0.2turn)',
	},
	{
		transform: 'rotateY(0)',
	},
];

export const animationTiming = {
	duration: 500,
	iterations: 1,
};

export const Container = styled.section`
	display: flex;

	// justify-content: center;
	align-items: center;
	width: 90%;
	height: ${({ theme }) => theme.size.SIZE_280};

	z-index: ${({ theme }) => theme.zIndex.POPULAR_ARTICLES};
	margin: 0 auto;
	overflow: hidden;
	scroll-snap-type: x proximity;
	-webkit-overflow-scrolling: touch;

	&::-webkit-scrollbar {
		opacity: 0;
	}
	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_SMALL}) {
		width: 60%;
		height: ${({ theme }) => theme.size.SIZE_280};
	}
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

export const LeftArrowButton = styled(AiOutlineLeft)`
	position: absolute;
	opacity: 0.5;

	cursor: pointer;

	&:hover,
	&:active {
		opacity: 1;
	}

	${({ theme }) => css`
		left: ${theme.size.SIZE_004};

		font-size: ${theme.size.SIZE_020};
		color: ${theme.colors.BLACK_600};

		z-index: ${theme.zIndex.ARTICLE_ARROW_BUTTON};
	`}

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_SMALL}) {
		left: ${({ theme }) => theme.size.SIZE_140};
	}
`;

export const RightArrowButton = styled(AiOutlineLeft)`
	position: absolute;
	opacity: 0.5;

	cursor: pointer;

	transform: rotate(180deg);

	&:hover,
	&:active {
		opacity: 1;
	}
	${({ theme }) => css`
		right: ${theme.size.SIZE_004};

		font-size: ${theme.size.SIZE_020};

		color: ${theme.colors.BLACK_600};
		z-index: ${theme.zIndex.ARTICLE_ARROW_BUTTON};
	`}

	@media (min-width: ${({ theme }) => theme.breakpoints.DESKTOP_SMALL}) {
		right: ${({ theme }) => theme.size.SIZE_140};
	}
`;

export const ArticleContent = styled.div`
	display: flex;

	justify-content: space-between;
	width: 100%;
	gap: 1rem;
	padding: 1rem;
	${({ theme }) => css`
		border-radius: ${theme.size.SIZE_010};

		padding: ${theme.size.SIZE_010};
		z-index: ${theme.zIndex.ARTICLE_POPULAR_CONTENT};
	`}
`;

export const Title = styled.h2`
	width: 100%;
	line-height: normal;
	text-overflow: ellipsis;
	white-space: nowrap;
	overflow: hidden;

	${({ theme }) => css`
		height: ${theme.size.SIZE_040};

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

export const CommentIcon = styled(AiOutlineMessage)`
	font-size: ${({ theme }) => theme.size.SIZE_016};

	color: ${({ theme }) => theme.colors.BLACK_600};
`;
