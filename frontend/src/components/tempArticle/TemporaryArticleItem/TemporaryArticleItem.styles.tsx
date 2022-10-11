import { theme } from '@/styles/Theme';
import styled from '@emotion/styled';

export const Container = styled.section`
	display: flex;
	flex-direction: column;

	width: 85%;
	height: ${({ theme }) => theme.size.SIZE_050};

	border: ${({ theme }) => theme.size.SIZE_001} solid ${({ theme }) => theme.colors.BLACK_200};
	border-radius: ${({ theme }) => theme.size.SIZE_004};
`;

export const Title = styled.h2`
	display: block;
	width: 90%;

	font-size: ${({ theme }) => theme.size.SIZE_016};

	text-overflow: ellipsis;
	overflow: hidden;
	word-break: break-all;
	white-space: nowrap;

	border-bottom: ${({ theme }) => theme.size.SIZE_001} solid ${({ theme }) => theme.colors.GRAY_500};

	padding: ${({ theme }) => theme.size.SIZE_004} 0 ${({ theme }) => theme.size.SIZE_014}
		${({ theme }) => theme.size.SIZE_004};
`;

export const CreatedAt = styled.div`
	font-size: ${({ theme }) => theme.size.SIZE_010};
`;

export const SubInfo = styled.div`
	display: flex;
	gap: ${({ theme }) => theme.size.SIZE_010};
	padding: ${({ theme }) => theme.size.SIZE_002};

	align-items: center;
`;

export const Category = styled.div<{ isQuestion: boolean }>`
	font-size: ${({ theme }) => theme.size.SIZE_012};
	background-color: ${(props) => (props.isQuestion ? theme.colors.RED_500 : theme.colors.BLUE_500)};
	padding: ${({ theme }) => theme.size.SIZE_004};
	border-radius: ${({ theme }) => theme.size.SIZE_004};
`;
