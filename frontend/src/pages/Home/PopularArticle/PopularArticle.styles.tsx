import { articleColors } from '@/styles/Theme';
import styled from '@emotion/styled';
import { MdArrowBackIosNew } from 'react-icons/md';
import { FaRegCommentDots } from 'react-icons/fa';

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
	position: relative;
	display: flex;
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
	background-color: ${({ theme, colorKey }) => theme.articleColors[colorKey]};
	z-index: ${({ theme }) => theme.zIndex.ARTICLE_BACKGROUND_CONTENT};
	border-radius: ${({ theme }) => theme.size.SIZE_010};
`;

export const RightBackgroundArticle = styled.div<{ colorKey: keyof typeof articleColors }>`
	position: absolute;
	top: 0;
	right: 0;
	width: 50%;
	height: 100%;
	background-color: ${({ theme, colorKey }) => theme.articleColors[colorKey]};
	z-index: ${({ theme }) => theme.zIndex.ARTICLE_BACKGROUND_CONTENT};
	border-radius: ${({ theme }) => theme.size.SIZE_010};
`;

export const LeftArrowButton = styled(MdArrowBackIosNew)`
	position: absolute;
	left: ${({ theme }) => theme.size.SIZE_002};
	font-size: ${({ theme }) => theme.size.SIZE_020};
	color: ${({ theme }) => theme.colors.BLACK_600};
	opacity: 0.5;
	z-index: ${({ theme }) => theme.zIndex.ARTICLE_ARROW_BUTTON};

	cursor: pointer;

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
	z-index: ${({ theme }) => theme.zIndex.ARTICLE_ARROW_BUTTON};

	opacity: 0.5;
	transform: rotate(180deg);

	cursor: pointer;

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
	background-color: ${({ theme, colorKey }) => theme.articleColors[colorKey]};
	border-radius: ${({ theme }) => theme.size.SIZE_010};
	padding: ${({ theme }) => theme.size.SIZE_010};
	z-index: ${({ theme }) => theme.zIndex.ARTICLE_POPULAR_CONTENT};
`;

export const Title = styled.h2`
	font-size: ${({ theme }) => theme.size.SIZE_014};
	width: 100%;
	height: ${({ theme }) => theme.size.SIZE_040};
	line-height: normal;
	text-overflow: ellipsis;
	overflow: hidden;
	white-space: nowrap;
	margin-top: ${({ theme }) => theme.size.SIZE_004};
`;

export const ArticleInfo = styled.div`
	display: flex;
	flex-direction: row;
	justify-content: space-between;
`;

export const ProfileBox = styled.div`
	display: flex;
	align-items: center;
	flex-direction: row;
	gap: ${({ theme }) => theme.size.SIZE_006};
`;

export const UserImg = styled.img`
	width: ${({ theme }) => theme.size.SIZE_024};
	height: ${({ theme }) => theme.size.SIZE_024};
	object-fit: cover;
	object-position: center;
	border-radius: 50%;
`;

export const UserName = styled.span`
	font-size: ${({ theme }) => theme.size.SIZE_012};
	color: ${({ theme }) => theme.colors.BLACK_600};
`;

export const CommentBox = styled.div`
	display: flex;
	flex-direction: row;
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
