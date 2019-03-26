import java.util.Arrays;
import java.util.stream.Collectors;

class Term {
    public int coef;
    public int exp;

    public Term(int coef, int exp) {
        this.coef = coef;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return coef + "x^" + exp;
    }
}
public class Poly_C {

	private Term[] terms;
	private int next = 0;
	
	public Poly_C(int termCount){

		if (termCount == 0){
			this.terms = new Term[1];
			this.terms[0] = new Term(0,0);
		}
		else{
			this.terms = new Term[termCount];
                	for(int i = 0; i<termCount; i++){
				this.terms[i] = new Term(0, 0);
			}
		}
	}

   	public Poly_C(int termCount, Term... terms) {
       		this(termCount);

        	if (termCount < terms.length)
                    throw new IllegalArgumentException("termCount < terms.length");

        	for (int i = 0; i < terms.length; i++) {
            	    addTerm(terms[i].coef, terms[i].exp);
        	}
    	}
  
	public int degree(){
		int enable_index_num = this.terms.length;
		int max_exp = 0;
		for(int i=0; i<enable_index_num; i++){
			if (terms[i].exp > max_exp){
				max_exp = terms[i].exp;
			}
		}

		return max_exp;
	}

        public int getTermCount() {
        	return next;
        }

        public Poly_C add(Poly_C other) {
		int total_len = get_len(this, other);
		System.out.println(total_len);
		int[] len = {init_len(this), init_len(other)};
		int i=0, j=0, k=0;
		Poly_C temp_poly = new Poly_C(total_len);
		
		temp_poly.init_poly(temp_poly, temp_poly.terms.length);
		System.out.println(len[1]);
		while(i<len[0] && j<len[1]){
			if(this.terms[i].exp == other.terms[j].exp){
				temp_poly.terms[k].coef = this.terms[i].coef + other.terms[j].coef;
				temp_poly.terms[k].exp = this.terms[i].exp;
				k++;
				i++;
				j++;
				temp_poly.next++;

				
			}
			else if(this.terms[i].exp > other.terms[j].exp){
				temp_poly.terms[k].coef = this.terms[i].coef;
				temp_poly.terms[k].exp = this.terms[i].exp;
				k++;
				i++;
				temp_poly.next++;
			}
			else if(this.terms[i].exp < other.terms[j].exp){
				temp_poly.terms[k].coef = other.terms[j].coef;
				temp_poly.terms[k].exp = other.terms[j].exp;
				k++;
				j++;
				temp_poly.next++;
			}

		}

		if(i > j){
			while(k<total_len){
				temp_poly.terms[k].coef = other.terms[j].coef;
				temp_poly.terms[k].exp = other.terms[j].exp;
				k++;
				j++;
				temp_poly.next++;
			}
		}
		else{
			while(k<total_len){
				temp_poly.terms[k].coef = this.terms[i].coef;
				temp_poly.terms[k].exp = this.terms[i].exp;
				k++;
				i++;
				temp_poly.next++;
			}	
		}		
		
		
		return temp_poly;		      
        }
	
	public int get_len(Poly_C poly1, Poly_C poly2){
		int long_len = 0;
		int[] len = {init_len(poly1), init_len(poly2) };
		int total_len = len[0]+len[1];
		int copy_len = total_len;

		int[] temp_exp = new int[total_len];
		for(int i=0; i<len[0]; i++){
			temp_exp[i] = poly1.terms[i].exp;
		}
		for(int i=0; i<len[1]; i++){
			temp_exp[i+len[0]] = poly2.terms[i].exp;
		}

		System.out.println(Arrays.toString(temp_exp));
		System.out.println(copy_len);
		for(int i=0; i<total_len; i++){
			System.out.println("################");
			for(int j=i+1; j<total_len; j++){
				if(temp_exp[i] == temp_exp[j] && temp_exp[j]!=-1){
					copy_len--;
					System.out.println(copy_len);
					temp_exp[j] = -1;
				}
			}

		}
		System.out.println(copy_len);
		System.out.println(Arrays.toString(temp_exp));
		return copy_len;
					
	}

	public void init_poly(Poly_C poly, int len){
		for(int k=0; k<len; k++){
			poly.terms[k] = new Term(0,0);
		}
	}
	public int init_len(Poly_C poly){
		int len_count = 0;
		for(int i=0; i<poly.terms.length; i++){
			if(poly.terms[i].coef == 0){
				return len_count;
			}
			len_count++;
		}
		return len_count;
	}
				
	public void addTerm(int coef, int exponent) {
		int enable_index_num = this.terms.length;
		
		if (exponent > enable_index_num){
			System.out.println("Over exponent!");
		}
		else{
			for(int i=0; i<enable_index_num; i++){
				if(this.terms[i].coef == 0){
					this.terms[i].coef = coef;
					this.terms[i].exp = exponent;
					break;
				}
			}
		}
		next++;
	        Arrays.sort(terms, 0, next, (a, b) -> b.exp - a.exp);
	}

    public Poly_C mult(Poly_C other) {
	int max_len = this.terms.length*other.terms.length;
	int[] temp_exp = new int[max_len];
	int[] temp_coef = new int[max_len];
	int[] temp_count = new int[max_len];
	int exp_count = 0, k=0;
	int temp_sum=0;
	int tail=0;



	for(int z=0; z<max_len; z++){
		temp_exp[z]=0;
		temp_coef[z]=0;
		temp_count[z]=0;
	}
	for(int i=0; i<this.terms.length; i++){
		for(int j=0; j<other.terms.length; j++){
			temp_exp[k] = this.terms[i].exp + other.terms[j].exp;
			temp_coef[k] = this.terms[i].coef*other.terms[j].coef;
			k++;
		}
	}
	System.arraycopy(temp_exp, 0, temp_count, 0, temp_exp.length);
	k=0;
	System.out.println(Arrays.toString(temp_exp));
	System.out.println(Arrays.toString(temp_coef));
	for(int i=0; i<max_len; i++){
		for(int j=i+1; j<max_len; j++){
			if(temp_exp[i] == temp_exp[j]){	
				temp_count[j] = 0;
			}
		}
		if(temp_count[i]!=0){
			exp_count++;
		}
		
	}
	for(int i=0; i<max_len; i++){
		if(temp_exp[i] == 0 && temp_coef[i] != 0){
			exp_count++;
			tail = temp_coef[i];
			break;
		}
	}

	Poly_C temp_poly = new Poly_C(exp_count);

	temp_poly.init_poly(temp_poly, temp_poly.terms.length);
	System.out.println(exp_count);
	exp_count=0;
	for(int i=0; i<max_len; i++){
		temp_sum = temp_coef[i];
		for(int j=i+1; j<max_len; j++){
			if(temp_exp[i] == temp_exp[j] && temp_exp[i] != 0){
				temp_sum = temp_sum + temp_coef[j];
				temp_exp[j] = 0;
			}
		}

		if(temp_exp[i] != 0){
			temp_poly.terms[exp_count].exp = temp_exp[i];
			temp_poly.terms[exp_count].coef = temp_sum;
			exp_count++;
		}
		temp_sum = 0;
	}
	if(tail!=0){
	    temp_poly.terms[exp_count].coef = tail;
	    temp_poly.next = exp_count+1;
	}
	else{
	    temp_poly.next = exp_count;
	}
	return temp_poly;
		
    }

    public String toString() {
        Arrays.sort(terms, 0, next, (a, b) -> b.exp - a.exp);
        return Arrays.stream(terms)
                     .filter(i -> i != null)
                     .map(i -> i.toString())
                     .collect(Collectors.joining(" + "));
    }
				
		

	public static void main(String... args){

	        Poly_C poly1 = new Poly_C(4);
	        poly1.addTerm(1, 3);
	        poly1.addTerm(1, 2);
	        poly1.addTerm(1, 1);
	        poly1.addTerm(1, 0);

	        Poly_C poly2 = new Poly_C(4);
      		poly2.addTerm(1, 1);
        	poly2.addTerm(1, 2);
        	poly2.addTerm(1, 3);
        	poly2.addTerm(1, 0);

		System.out.println(poly1);
		System.out.println(poly2);

	        Poly_C poly3 = poly1.add(poly2);
		System.out.println(poly3.toString());		

		Poly_C poly4 = poly1.mult(poly2);
		System.out.println(poly4.toString());

	}
}

	
