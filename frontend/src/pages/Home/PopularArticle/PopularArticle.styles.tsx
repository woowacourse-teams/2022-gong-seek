import { FaRegCommentDots } from 'react-icons/fa';
import { MdArrowBackIosNew } from 'react-icons/md';

import { articleColors } from '@/styles/Theme';
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
	position: relative;

	justify-content: center;
	align-items: center;

	width: 100%;
	height: ${({ theme }) => theme.size.SIZE_080};
`;

export const LeftBackgroundArticle = styled.div<{ colorKey: keyof typeof articleColors }>`
	position: absolute;

	top: 0;
	left: 0;

	width: 50%;
	height: 100%;

	border-radius: ${({ theme }) => theme.size.SIZE_010};

	background-color: ${({ theme, colorKey }) => theme.articleColors[colorKey]};

	z-index: ${({ theme }) => theme.zIndex.ARTICLE_BACKGROUND_CONTENT};
`;

export const RightBackgroundArticle = styled.div<{ colorKey: keyof typeof articleColors }>`
	position: absolute;

	top: 0;
	right: 0;

	width: 50%;
	height: 100%;

	border-radius: ${({ theme }) => theme.size.SIZE_010};

	background-color: ${({ theme, colorKey }) => theme.articleColors[colorKey]};

	z-index: ${({ theme }) => theme.zIndex.ARTICLE_BACKGROUND_CONTENT};
`;

export const LeftArrowButton = styled(MdArrowBackIosNew)`
	position: absolute;

	left: ${({ theme }) => theme.size.SIZE_002};

	font-size: ${({ theme }) => theme.size.SIZE_020};

	color: ${({ theme }) => theme.colors.BLACK_600};
	opacity: 0.5;

	cursor: pointer;

	z-index: ${({ theme }) => theme.zIndex.ARTICLE_ARROW_BUTTON};

	&:hover,
	&:active {
		opacity: 1;
	}
`;

export const RightArrowButton = styled(MdArrowBackIosNew)`
	position: absolute;

	right: ${({ theme }) => theme.size.SIZE_002};

	font-size: ${({ theme }) => theme.size.SIZE_020};

	color: ${({ theme }) => theme.colors.BLACK_600};
	opacity: 0.5;

	cursor: pointer;

	z-index: ${({ theme }) => theme.zIndex.ARTICLE_ARROW_BUTTON};
	transform: rotate(180deg);

	&:hover,
	&:active {
		opacity: 1;
	}
`;

export const ArticleContent = styled.div<{ colorKey: keyof typeof articleColors }>`
	display: flex;

	flex-direction: column;
	justify-content: space-between;

	width: 80%;
	height: 100%;

	border-radius: ${({ theme }) => theme.size.SIZE_010};

	background-color: ${({ theme, colorKey }) => theme.articleColors[colorKey]};

	padding: ${({ theme }) => theme.size.SIZE_010};

	z-index: ${({ theme }) => theme.zIndex.ARTICLE_POPULAR_CONTENT};
`;

export const Title = styled.h2`
	width: 100%;
	height: ${({ theme }) => theme.size.SIZE_040};

	font-size: ${({ theme }) => theme.size.SIZE_014};
	line-height: normal;
	text-overflow: ellipsis;
	white-space: nowrap;
	overflow: hidden;

	margin-top: ${({ theme }) => theme.size.SIZE_004};
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

export const CommentIcon = styled(FaRegCommentDots)`
	font-size: ${({ theme }) => theme.size.SIZE_016};

	color: ${({ theme }) => theme.colors.BLACK_600};
`;

export const EmptyText = styled.div`
	margin: 0 auto;
	font-size: ${({ theme }) => theme.size.SIZE_018};
`;
